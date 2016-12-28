package app;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.NoSuchElementException;
import javax.sql.DataSource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller for Contact.java
 *
 * Next
 * 1. Add Angular front-end
 * 2. Deploy to AWS
 */
@RestController
public class ContactController {

    private DataSource ds;

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * C of CRUD
     * Receives a Contact as JSON object
     * @return HTTP Status 201 if success and new location header
     */
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public ResponseEntity<Void> add(@RequestBody Contact c) {
        //? protects against SQL injection attacks by instructing
        //JDBC to bind variables
        String s1 = "INSERT INTO contacts (name, number) VALUES (?, ?)";
        jdbc.update(s1, c.getName(), c.getNumber());

        String s2 = "SELECT id FROM contacts WHERE name = ?";
        int id = jdbc.queryForObject(s2, Integer.class, c.getName());

        UriComponentsBuilder ub = UriComponentsBuilder.newInstance();
        UriComponents u = ub.path("/contacts/{id}").buildAndExpand(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(u.toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * R of CRUD
     * @return HTTP Status 200 and List of all contacts as JSON object
     */
    @RequestMapping(value="/contacts", method= RequestMethod.GET)
    public ResponseEntity<List> getAll() {
        String s = "SELECT * FROM contacts";
        List<Map<String, Object>> list = jdbc.queryForList(s);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * R of CRUD
     * @return HTTP Status 200 if success and desired contact as JSON object
     */
    @RequestMapping(value="/contacts/{id}", method= RequestMethod.GET)
    public ResponseEntity<Contact> getOne(@PathVariable("id") Long id) {
        String s = "SELECT * FROM contacts WHERE id = ?";
        Contact c = jdbc.queryForObject(s,
                new BeanPropertyRowMapper<>(Contact.class), id);

        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    /**
     * U of CRUD
     * Updates the contact's name and number
     * @return HTTP Status 200 if success
     */
    @RequestMapping(value="/contacts/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Contact c) {
        String s = "UPDATE contacts SET name = ?, number = ? "
                + "WHERE id = ?";
        jdbc.update(s, c.getName(), c.getNumber(), id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * D of CRUD
     * Deletes the specified contact
     * @return HTTP Status 200 if success and success message as JSON object
     */
    @RequestMapping(value="/contacts/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Map> delete(@PathVariable("id") Long id) {
        String s = "DELETE FROM contacts WHERE id = ?";
        jdbc.update(s, id);

        Map<String, String> message = new HashMap<>();
        message.put("message", "contact " + id + " deleted");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Allows injection of a production SQL database for persistent
     * storage
     *
     * This method is never used. Database connection is established in
     * Application.java with the @Bean annotation and the corsFilter()
     * method
     */
    public void setDataSource(DataSource ds) {
        this.ds = ds;
        this.jdbc = new JdbcTemplate(ds);
    }

}
