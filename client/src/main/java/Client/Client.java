package Client;

import definition.Greeter;
import org.osgi.framework.*;

import java.util.Scanner;

public class Client implements BundleActivator, ServiceListener {

    private BundleContext ctx;
    private ServiceReference serviceReference;

    public void start(BundleContext ctx) {
        System.out.println("запущен клиент");
        this.ctx = ctx;
        try {
            ctx.addServiceListener(this, "(objectclass=" + Greeter.class.getName() + ")");
        } catch (InvalidSyntaxException ise) {
            ise.printStackTrace();
        }
    }

    public void stop(BundleContext bundleContext) {
        System.out.println("Остановлен клент");
        if (serviceReference != null) {
            ctx.ungetService(serviceReference);
        }
        this.ctx = null;
    }

    public void serviceChanged(ServiceEvent serviceEvent) {
        int type = serviceEvent.getType();
        switch (type) {
            case (ServiceEvent.REGISTERED):
                System.out.println("Notification of service registered.");
                serviceReference = serviceEvent.getServiceReference();
                Greeter service = (Greeter) (ctx.getService(serviceReference));
               /* Scanner sc = new Scanner(System.in);
                String name = sc.nextLine();
                System.out.println(service.sayHiTo(name));*/
                System.out.println(service.sayHiTo("Alex"));
                break;
            case (ServiceEvent.UNREGISTERING):
                System.out.println("Notification of service unregistered.");
                ctx.ungetService(serviceEvent.getServiceReference());
                break;
            default:
                break;
        }
    }
}