package app;

import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




/**
 * Controller for Item.java
 *
 * Notes
 * 1. Currently this sends strings via @RequestBody, you want to send
 * JSON
 * 2. Look at HTTP method protocols and make sure that your implementation
 * matches (eg should DELETE return the deleted item?)
 * 3. Use ResponseEntity for all returned objects
 */
@RestController
public class ItemController {

    private final AtomicLong COUNTER = new AtomicLong();
    private Set<Item> items;
    
    public ItemController() {
        items = new HashSet<>();
    }

    /**
     * C of CRUD
     * This method works if you send a raw POST request in Postman
     * and type any string because of (@RequestBody String value), but
     * you want to be able to send JSON to the backend.
     *
     * The app correctly returns a JSON object.
     */
    @RequestMapping(value="/newItem", method= RequestMethod.POST)
    public Item addItem(@RequestBody String value) {
        Item newItem = new Item(COUNTER.incrementAndGet(), value);
        items.add(newItem);
        return newItem;
    }

    /**
     * R of CRUD
     * This method works as expected and returns a JSON object. It
     * should use ResponseEntity instead of returning the item.
     */
    @RequestMapping(value="/items", method= RequestMethod.GET)
    public Set<Item> itemsItems() {
        return items;
    }

    /**
     * R of CRUD
     * This method works as expected and returns a JSON object.
     * Good use of ResponseEntity.
     */
    @RequestMapping(value="/items/{id}", method= RequestMethod.GET)
    public ResponseEntity<Item> getItem(@PathVariable("id") Long id) throws NoSuchElementException {
        //simple example, but you need to connect a database
        //this is time-intensive
        for (Item item : items) {
            if (item.getID() == id)
                return new ResponseEntity<Item>(item, HttpStatus.OK);
        }
        
        throw new NoSuchElementException();
    }

    /**
     * U of CRUD
     * Updates the specified item, but returns a NoSuchElementException.
     */
    @RequestMapping(value="/updateItem/{id}", method= RequestMethod.PUT)
    public Item updateItem(@PathVariable("id") Long id, @RequestBody String value)
            throws NoSuchElementException {
        for (Item item : items) {
            if (item.getID() == id)
                item.setValue(value);
        }

        throw new NoSuchElementException();
    }

    /**
     * D of CRUD
     * Deletes the specified item, but returns a ConcurrentModificationException.
     */
    @RequestMapping(value="/deleteItem/{id}", method= RequestMethod.DELETE)
    public void deleteItem(@PathVariable("id") Long id) throws NoSuchElementException {
        for (Item item : items) {
            if (item.getID() == id)
                items.remove(item);
        }

        throw new NoSuchElementException();
    }
}
