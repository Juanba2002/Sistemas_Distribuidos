import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {

        ServerSocket servidor=null;
        Socket sc=null;
        DataInputStream in;
        DataOutputStream out;
        final int puerto=5000;

        try {
            servidor= new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            while(true)
            {
                sc=servidor.accept();//Se queda a la espera
                System.out.println("Cliente conectado");
                in= new DataInputStream(sc.getInputStream());//Crear puente del cliente al servidor
                out=new DataOutputStream(sc.getOutputStream());//Crear puente del servidor al cliente
                String mensaje=in.readUTF();//Espera a que el cliente envie algo
                System.out.println(mensaje);
                out.writeUTF("Hola mundo desde el servidor");
                sc.close();
                System.out.println("Cliente desconectado");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}