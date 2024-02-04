import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_1 {
    public static void main(String[] args){
        ServerSocket servidor=null;
        Socket sc=null;
        final int puerto=5002;
        DataInputStream in;
        DataOutputStream out;

        try {
            servidor= new ServerSocket(puerto);
            System.out.println("Servidor1 iniciado");

            while(true)
            {
                sc=servidor.accept();//Se queda a la espera
                System.out.println("Servidor princiopal conectado");
                in= new DataInputStream(sc.getInputStream());//Crear puente del cliente al servidor
                out=new DataOutputStream(sc.getOutputStream());//Crear puente del servidor al cliente
                int longitudArreglo = in.readInt();
                int[] arregloNumeros = new int[longitudArreglo];
                for (int i = 0; i < longitudArreglo; i++) {
                    arregloNumeros[i] = in.readInt();
                }
                int sum=0;
                for(int num:arregloNumeros)
                {
                    System.out.println(num);
                }
                for(int i=0;i<longitudArreglo;i++)
                {
                    sum+=arregloNumeros[i];
                }
                System.out.println(sum);
                out.writeInt(sum);
                sc.close();
                System.out.println("Servidor princpipal desconectado");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
