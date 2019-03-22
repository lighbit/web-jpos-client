package iso8583.jposweb.webjposclient;

import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class WebJposClientApplication {
	public static void startq2() throws InterruptedException {
		Q2 q2 = new Q2();
		q2.start();
		System.out.println("Server Running!");
	}

	public static void main(String[] args) throws Exception {
		startq2();
		SpringApplication.run(WebJposClientApplication.class, args);
	}

	@Bean
	public QMUX exposeQmux() throws Exception{
		return (QMUX) NameRegistrar.get("mux.kisel");
	}

}
