package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    private static String staticFilesPath = "webroot";

    public static void main(String[] args) throws IOException, URISyntaxException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8082);
        } catch (IOException e) {
            System.err.println("No se pudo abrir el puerto 8082.");
            System.exit(1);
        }

        System.out.println("Servidor HTTP iniciado en http://localhost:8082");
        boolean running = true;

        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Esperando conexión...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Error aceptando conexión.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            Map<String, String> parameters = new HashMap<>();
            String reqpath = "/";
            boolean isFirstLine = true;
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibido: " + inputLine);

                if (isFirstLine) {
                    String[] tokens = inputLine.split(" ");
                    URI uri = new URI(tokens[1]);
                    reqpath = uri.getPath();

                    System.out.println("Ruta solicitada: " + reqpath);

                    String query = uri.getQuery();
                    if (query != null) {
                        for (String param : query.split("&")) {
                            String[] kv = param.split("=");
                            if (kv.length == 2) {
                                parameters.put(kv[0], kv[1]);
                            }
                        }
                    }
                    isFirstLine = false;
                }

                if (!in.ready()) break;
            }

            HttpRequest request = new HttpRequest(parameters);
            WebMethod handler = WebFramework.getRoute(reqpath);

            if (handler != null) {
                String resultado = handler.execute(request, null);
                String response =
                    "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n\r\n" +
                    "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">" +
                    "<title>Respuesta</title></head><body>" +
                    resultado +
                    "</body></html>";
                out.println(response);

            } else {
                serveStaticFile(reqpath, out, clientSocket.getOutputStream());
            }

            out.close();
            in.close();
            clientSocket.close();
        }

        serverSocket.close();
    }

    private static void serveStaticFile(String reqpath, PrintWriter out, OutputStream rawOut) throws IOException {
        String filePath = staticFilesPath + (reqpath.equals("/") ? "/index.html" : reqpath);
        String contentType = getContentType(filePath);

        InputStream fileStream = HttpServer.class.getResourceAsStream("/" + filePath);

        if (fileStream == null) {
            out.println("HTTP/1.1 404 Not Found\r\n\r\nArchivo no encontrado: " + reqpath);
            out.flush();
            return;
        }

        if (contentType.startsWith("image/")) {
            byte[] fileBytes = fileStream.readAllBytes();
            String headers =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + fileBytes.length + "\r\n" +
                "\r\n";
            rawOut.write(headers.getBytes());
            rawOut.write(fileBytes);
            rawOut.flush();

        } else {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = fileReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            out.println(
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + contentType + "\r\n\r\n" +
                content.toString()
            );
            out.flush();
        }
    }

    private static String getContentType(String filePath) {
        if (filePath.endsWith(".html")) return "text/html";
        if (filePath.endsWith(".css"))  return "text/css";
        if (filePath.endsWith(".js"))   return "application/javascript";
        if (filePath.endsWith(".png"))  return "image/png";
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
        if (filePath.endsWith(".ico"))  return "image/x-icon";
        return "text/plain";
    }

    public static void get(String path, WebMethod wm) {
        WebFramework.get(path, wm);
    }

    public static void staticfiles(String path) {
        staticFilesPath = path;
    }
}