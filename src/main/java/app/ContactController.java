package app;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controller for Contact.java
 *
 * Next
 * 1. Replace Map with Postgres database
 * 2. Add Angular front-end
 * 3. Deploy to AWS
 */
@RestController
public class ContactController {

    private Map<Long, Contact> contacts; //replace with a database
    private final AtomicLong COUNTER = new AtomicLong(); //primary key
    
    public ContactController() {
        contacts = new HashMap<>();
    }

    /**
     * C of CRUD
     * Receives a Contact as JSON object
     * @return HTTP Status 201 if success and new location header
     */
    @RequestMapping(value="/", method= RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Contact c) {
        long id = COUNTER.incrementAndGet();
        contacts.put(id, c);

        UriComponentsBuilder ub = UriComponentsBuilder.newInstance();
        UriComponents u = ub.path("/contacts/{id}").buildAndExpand(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(u.toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    /**
     * R of CRUD
     * @return HTTP Status 200 and Map of all contacts as JSON object
     */
    @RequestMapping(value="/contacts", method= RequestMethod.GET)
    public ResponseEntity<Map> getAll() {
        return new ResponseEntity<Map>(contacts, HttpStatus.OK);
    }

    /**
     * R of CRUD
     * @return HTTP Status 200 if success and desired contact as JSON object
     */
    @RequestMapping(value="/contacts/{id}", method= RequestMethod.GET)
    public ResponseEntity<Contact> getOne(@PathVariable("id") Long id)
            throws NoSuchElementException {
        if (contacts.get(id) != null) {
            return new ResponseEntity<Contact>(contacts.get(id), HttpStatus.OK);
        }
        
        throw new NoSuchElementException();
    }

    /**
     * U of CRUD
     * Updates the contact's name and number
     * @return HTTP Status 200 if success
     */
    @RequestMapping(value="/contacts/{id}", method= RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Contact c)
            throws NoSuchElementException {
        if (contacts.get(id) != null) {
            contacts.put(id, c);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        throw new NoSuchElementException();
    }

    /**
     * D of CRUD
     * Deletes the specified contact
     * @return HTTP Status 200 if success and success message as JSON object
     */
    @RequestMapping(value="/contacts/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Map> delete(@PathVariable("id") Long id)
            throws NoSuchElementException {
        if (contacts.get(id) != null) {
            contacts.remove(id);

            Map<String, String> message = new HashMap<>();
            message.put("message", "contact " + id + " deleted");
            return new ResponseEntity<Map>(message, HttpStatus.OK);
        }

        throw new NoSuchElementException();
    }
}
