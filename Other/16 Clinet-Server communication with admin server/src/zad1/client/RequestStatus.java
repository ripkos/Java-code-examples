package zad1.client;

public class RequestStatus extends Thread {
    private RequestHandler handler;
    public RequestStatus(RequestHandler requestHandler) {
        this.handler=requestHandler;
    }

    @Override
    public void run() {
        try {
            //int count =0;
            while(true) {
                //System.out.println("Iteration "+ count++);
                try {
                    handler.write("ping");
                }
                catch (Exception e){

                }
                Thread.sleep(2000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
