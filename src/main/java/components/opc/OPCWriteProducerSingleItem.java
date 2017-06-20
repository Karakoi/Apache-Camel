package components.opc;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.UnknownGroupException;
import utils.NumberParser;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OPCWriteProducerSingleItem extends DefaultProducer {

    //	private static final transient Logger LOG = LoggerFactory.getLogger(OPCWriteProducerSingleItem.class);
    private OPCEndpoint endpoint;
    // private TestEndpoint endpoint;
    // private OPCWrite opcWrite;
    private OPCWrite2 opcWrite;

    // public OPCWriteProducerSingleItem(OPCEndpoint endpoint) {
    // super(endpoint);
    // this.endpoint = endpoint;
    // }

    public OPCWriteProducerSingleItem(Endpoint endpoint) {
        super(endpoint);
        this.endpoint = (OPCEndpoint) endpoint;
    }

    @Override
    public Exchange createExchange(Exchange exchange) {
        Exchange exch = endpoint.createExchange();
        return super.createExchange(exch);
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        opcWrite = new OPCWrite2();
        opcWrite.init(endpoint.getHostName(), endpoint.getUserName(), endpoint.getUserPassword(), endpoint.getClsid());
        opcWrite.connect();
    }

    public void process(Exchange exchange) throws Exception {

        // ------------------------------------------
        // opcWrite.doWrite(endpoint.getItemForWrite(),
        // exchange.getIn().getBody(String.class),
        // endpoint.getConnTimeDelay());
        // ------------------------------------------

        // Викликає функцію на запис часто, але записуватимуться дані залежно
        // від часу вказаного у класі OPC WRITE
        ScheduledExecutorService writeThread = Executors.newSingleThreadScheduledExecutor();
        writeThread.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
                    // Парсер для того, щоб передати чисте число, без []
                    NumberParser pars = new NumberParser();
                    opcWrite.doWrite(endpoint.getItemForWrite(),
                            pars.findNumberAsString(exchange.getIn().getBody(String.class)), endpoint.getConnTimeDelay());
                    // ITS WORKS // Integer num = new Integer(new
                    // Random().nextInt(20));
                    // System.out.println("ЧИСЛОООО " + num.toString()); //
                    // opcWrite.doWrite(endpoint.getItemForWrite(),
                    // num.toString(), endpoint.getConnTimeDelay());

                } catch (JIException | IllegalArgumentException | UnknownHostException | InterruptedException
                        | NotConnectedException | DuplicateGroupException | AddFailedException
                        | UnknownGroupException e) {
//					e.printStackTrace();
                }

            }
        }, 2, 1, TimeUnit.SECONDS);

    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
