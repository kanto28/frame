package test_framework;
import framework.*;
import java.util.*;
import framework.annotation.*;
public class Emp {
    int idemp;
    String nom;
    String nee;
    String genre;

    public Emp(){  }
    public Emp(int idemp, String nom, String nee, String genre) {
        this.idemp = idemp;
        this.nom = nom;
        this.nee = nee;
        this.genre = genre;
    }

    @Url(url_map="save")
    public void insert(){
        
    }
    
    @Url(url_map="findall")
    public void findEmp(){
    }
    public void update(){
        
    }
    @Url(url_map="findall")
    public ModelView searchEmp(){
        ModelView mv=new ModelView();
        mv.addItem("1",new Emp(1,"emp","2001-08-09","M"));
        mv.addItem("2","String2");
        mv.setUrl("page1.jsp");
        return mv;
    }
}
//tokony mitovy anarana am'le attribu an'le class le name ao @ formulaire.