package com.mycompany.parcial.src;

import java.net.*;
import java.io.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {
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
            
            System.out.println("variable PATH despues del while: " + path);
            
            
            
            if (path.contains("/consulta?comando=")) {
                String command = path.substring(18);
            }

                       
            //while ((inputLine = in.readLine()) != null) {
                //System.out.println("Recibí: " + inputLine);
                //if (!in.ready()) {
               //     break;
              //  }
            //}

            outputLine = IndexBody();

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

    public static void UnaryInvoke(String clase, String methodName, String paramType, String paramValue) {

    }
    
    
    public static void Class(String className) {

    }
    
    public static void BinaryInvoke(Class nombre, String metodo, String tipo1, Object value1, String tipo2, Object value2){
        
    }
    
    public static void Invoke(String className, String methodName) {

    }

    
    
    public static String IndexBody() {

        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Form Example</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Form with GET</h1>\n"
                + "        <form action=\"/hello\">\n"
                + "            <label for=\"name\">Name:</label><br>\n"
                + "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n"
                + "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                + "        </form> \n"
                + "        <div id=\"getrespmsg\"></div>\n"
                + "\n"
                + "        <script>\n"
                + "            function loadGetMsg() {\n"
                + "                let nameVar = document.getElementById(\"name\").value;\n"
                + "                const xhttp = new XMLHttpRequest();\n"
                + "                xhttp.onload = function() {\n"

                + "                }\n"
                + "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n"
                + "                xhttp.send();\n"
                + "            }\n"
                + "        </script>\n"
                + "\n"
                + "        \n"
                + "        <div id=\"postrespmsg\"></div>\n"
                + "        \n"
                + "        <script>\n"
                + "            function loadPostMsg(name){\n"
                + "                let url = \"/hellopost?name=\" + name.value;\n"
                + "\n"
                + "                fetch (url, {method: 'POST'})\n"
                + "                    .then(x => x.text())\n"
                + "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n"
                + "            }\n"
                + "        </script>\n"
                + "    </body>\n"
                + "</html>";
    }

}
