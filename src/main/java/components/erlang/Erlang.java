package components.erlang;

import com.ericsson.otp.erlang.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Erlang {

    private static OtpConnection conn;
    public OtpErlangObject received;
    private final String peer;
    private final String cookie;

    public Erlang(String _peer, String _cookie) {
        peer = _peer;
        cookie = _cookie;

    }

    private void connect() {
        System.out.print("Please wait, connecting to " + peer + "....\n");

        String javaClient = "java";
        try {
            OtpSelf self = new OtpSelf(javaClient, cookie.trim());
            OtpPeer other = new OtpPeer(peer.trim());
            conn = self.connect(other);
            System.out.println("Connection Established with " + peer + "\n");
        } catch (Exception exp) {
            System.out.println("connection error is :" + exp.toString());
            exp.printStackTrace();
        }
    }

    public void disconnect() {
        System.out.println("Disconnecting....");
        if (conn != null) {
            conn.close();
        }
        System.out.println("Successfuly Disconnected");
    }

    public OtpErlangObject invokeErlangTranslateFunction(String data) throws IOException, OtpErlangExit, OtpAuthException, OtpErlangDecodeException {
        conn.sendRPC("translator", "translate", new OtpErlangObject[]{
                new OtpErlangAtom(data),
                new OtpErlangAtom("Spanish")
        });
        OtpErlangObject response = conn.receiveMsg().getMsg();
        System.out.println("answer: " + response.toString());
        return response;
    }

    public OtpErlangObject invokeErlangHelloFunction() throws IOException, OtpErlangExit, OtpAuthException, OtpErlangDecodeException {
        conn.sendRPC("main", "hello", new OtpErlangObject[]{});
        OtpErlangObject response = conn.receiveMsg().getMsg();
        System.out.println("answer: " + response.toString());
        return response;
    }

    public OtpErlangObject invokeErlangSqrtFunction(String data) throws IOException, OtpErlangExit, OtpAuthException, OtpErlangDecodeException {
        int number = Integer.valueOf(data);
        log.info("numder: {}", number);
        conn.sendRPC("math", "sqrt", new OtpErlangObject[]{new OtpErlangLong(number)});
//        log.info("ANSWER: {}", conn.receiveMsg().getMsg());
        return conn.receiveMsg().getMsg();
    }

    public OtpErlangObject invokeErlangAddFunction(String data) throws IOException, OtpErlangExit, OtpAuthException, OtpErlangDecodeException {
        int number = Integer.valueOf(data);
        conn.sendRPC("math", "add", new OtpErlangObject[]{new OtpErlangLong(number)});
        return conn.receiveMsg().getMsg();
    }

    public OtpErlangObject invokeErlangFactFunction(String data) throws IOException, OtpErlangExit, OtpAuthException, OtpErlangDecodeException {
        int number = Integer.valueOf(data);
        conn.sendRPC("math", "fact", new OtpErlangObject[]{new OtpErlangLong(number)});
        OtpErlangObject response = conn.receiveMsg().getMsg();
        System.out.println("Fact(" + number + ")" + "= " + response);
        return response;
    }

    public OtpErlangObject invokeErlangListFunction(String data) throws IOException, OtpErlangExit, OtpAuthException, OtpErlangDecodeException {
        conn.sendRPC("math", "list", new OtpErlangObject[]{});
        OtpErlangObject response = conn.receiveMsg().getMsg();
        System.out.println("List " + response);

        OtpErlangTuple tuple = new OtpErlangTuple(response);
        System.out.println("tuple " + tuple.elementAt(0));

        //Trying to convert this data
//       SimpleErlangConverter converter = new SimpleErlangConverter();
//       Object obj = converter.fromErlang(response);
//        System.out.println("Object " + obj);
        return response;
    }


    public String writeAndReadData(String operationName, String data) throws IOException {
        connect();
        try {
            switch (operationName) {
                case "sqrt":
                    return invokeErlangSqrtFunction(data).toString();
                case "Add":
                    return invokeErlangAddFunction(data).toString();
                case "List":
                    return invokeErlangListFunction(data).toString();
                case "Translate":
                    return invokeErlangTranslateFunction(data).toString();
                case "factorial":
                    return invokeErlangFactFunction(data).toString();
                default:
                    return invokeErlangHelloFunction().toString();
            }
        } catch (IOException | OtpErlangExit | OtpErlangDecodeException | OtpAuthException e) {
            e.printStackTrace();
        }
        /*Do Calls to Rpc methods and then close the connection*/
        disconnect();
        return "";
    }


}
