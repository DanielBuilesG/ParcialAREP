package com.mycompany.parcial.src;

import java.net.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HttpServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = null;
        while (true) {
            try {
                serverSocket = new ServerSocket(36000);
            } catch (IOException e) {
                System.err.println("Could not listen on port: 35000.");
                System.exit(1);
            }

            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            String path = "";

            boolean firstLine = true;
            while ((inputLine = in.readLine()) != null) {
                if (firstLine && inputLine.contains("HTTP/1.1")) {
                    path = inputLine.split(" ")[1];
                    firstLine = false;
                }
                if (!in.ready()) {
                    break;
                }
            }

            String response = "<ul>";
            
            if (path.contains("/consulta?comando=")) {
                String command = path.substring(18);
                if (path.contains("Class")) {
                    response += Class(command, response);
                } else if (path.contains("invoke")) {
                    response += Invoke(command, response);
                } else if (path.contains("unaryInvoke")) {
                    response += UnaryInvoke(command, response);
                } else if (path.contains("binaryInvoke")) {
                    response += BinaryInvoke(command, response);
                }
            }

            response += "</ul>";

            outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n" + IndexBody(response);

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }
    }

    public static Object GetRealValue(Object value, String type) {
        Object realValue = null;

        switch (type) {
            case "int":
                realValue = Integer.valueOf((String) value);
                break;
            case "String":
                realValue = (String) value;
                break;
            case "double":
                realValue = Double.valueOf((String) value);
                break;
            default:
                break;
        }
        return realValue;

    }

    public static String UnaryInvoke(String comando, String response) {
        return "Hola mundo";
    }

    public static String Class(String comando, String response) throws ClassNotFoundException {
        String metodoJava;
        Class c = Class.forName(metodoJava);
        Method[] method = c.getDeclaredMethods();
        Field[] field = c.getDeclaredFields();

        for(Method metodo : method) {
            response += "<li>" + method.getClass().getName()+ "</li>";
        }
        for(Field campos : field) {
            response += "<li>" + field.getClass().getName() + "</li>";

        }

       return response;
       
    }

    public static String BinaryInvoke(String comando, String response) {
        return "Hola mundo";
    }

    public static String Invoke(String comando, String methodName) {
        return "Hola mundo";
    }

    public static String IndexBody(String response) {

        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Form Example</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Name of the class</h1>\n" +
                "<form action=\"/consulta\">\n" +
                "    <label for=\"comando\">comando:</label><br>\n" +
                "    <input type=\"text\" id=\"comando\" name=\"comando\" value=\"Class(java.lang.Math)\"><br><br>\n" +
                "    <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "</form>\n" +
                "<div id=\"getrespmsg\">" + response +"</div>\n" +
                "\n" +
                "<script>\n" +
                "        function loadGetMsg() {\n" +
                "            let nameVar = document.getElementById(\"comando\").value;\n" +
                "            const xhttp = new XMLHttpRequest();\n" +
                "            xhttp.onload = function() {\n" +
                "                document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                this.responseText;\n" +
                "            }\n" +
                "            xhttp.open(\"GET\", \"/consulta?comando=\"+nameVar);\n" +
                "            xhttp.send();\n" +
                "        }\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
    }

}
