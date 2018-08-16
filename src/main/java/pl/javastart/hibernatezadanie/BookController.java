package pl.javastart.hibernatezadanie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.List;

@Controller
public class BookController {

    @PersistenceUnit
    @Autowired
    private EntityManagerFactory factory;

    @PostMapping("/addbook")
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
        Query query = entityManager.createQuery("SELECT k from Books k", Books.class );

        List<Books> books = query.getResultList();

        model.addAttribute("allbooks", books);

        model.addAttribute("newBook", new Books());
        return "Home";
    }

}