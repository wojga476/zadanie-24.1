package pl.javastart.hibernatezadanie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class BooksController {


    @PersistenceUnit
    @Autowired
    private EntityManagerFactory factory;

    @PostMapping("/addbooks")
    @ResponseBody
    public String dodaj(Books books) {

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(books);
        entityManager.getTransaction().commit();
        return "Dodano" + "<br/><br/><a href=\"/\">Kontynuuj dodawanie</a><br/>";
    }

    @GetMapping("/")
    public String home(Model model) {
        EntityManager entityManager = factory.createEntityManager();
        Query query = entityManager.createQuery("SELECT m from Books m", Books.class );

        List<Books> books = query.getResultList();

        model.addAttribute("allBooks", books);

        model.addAttribute("newBook", new Books());
        return "Home";
    }

}
