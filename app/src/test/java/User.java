/**
 * Classe abstraite User qui permet d'enregistrer les informations qui
 * sont communes a tout les utilisateurs de l'application.
 *
 * @author Chloé Al-Frenn
 */

public abstract class User {

    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;

    //getters
    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    //setters

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }


}
