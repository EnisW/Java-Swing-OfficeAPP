import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;
// "C:\\Users\\Ahmet Talha Geçgelen\\IdeaProjects\\untitled\\src\\credentials.txt"
public class Main {
    public static void main(String[] args) throws IOException {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter credentials file path: ");
        String path = stdin.nextLine();

        HashMap<String, String> credentialList = getCredentials(path);

        ServerSocket ss = new ServerSocket(5555);
        Socket s = ss.accept();

        DataInputStream inputStream = new DataInputStream(s.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
        String input = inputStream.readUTF();

        boolean passflag = false;

        String parsed[] = input.split(" ");
        for(String username: credentialList.keySet()){
            if(parsed[0].equals(username) && parsed[1].equals(credentialList.get(username))){
                outputStream.writeBoolean(true);
                System.out.println("Successful login: " + username);
                passflag = true;
                break;
            }
        }

        if (!passflag){
            outputStream.writeBoolean(false);
            System.out.println("Unsuccessful login attempt: " + parsed[0]);
        }

        s.close();
    }




    public static HashMap<String, String> getCredentials(String path) throws IOException {

        HashMap<String, String> credentialList = new HashMap<String, String>();
        FileReader fileIn = new FileReader(path);
        BufferedReader in = new BufferedReader(fileIn);
        String line = null;
        String parsed[];
        while ((line = in.readLine()) != null){
            parsed = line.split(" ");
            credentialList.put(parsed[0], parsed[1]);
        }

        return credentialList;
    }
    
}