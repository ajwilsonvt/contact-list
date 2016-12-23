package app;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Text.java
 */
@RestController
public class TextController {

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong COUNTER = new AtomicLong();

    @RequestMapping(value="/", method= RequestMethod.GET)
    public Text text(@RequestParam(value="name", defaultValue="World") String name) {
        return new Text(COUNTER.incrementAndGet(), String.format(TEMPLATE, name));
    }

}
