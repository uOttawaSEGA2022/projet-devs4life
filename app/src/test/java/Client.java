/**
 * Classe Client qui etends la classe abstraite User.
 * Elle permet d'enregistrer les informations qui sont uniques aux utilisateurs clients.
 *
 *
 * @author Chloé Al-Frenn
 */
public class Client extends User{

    protected String address;
    protected String cardNumber;
    protected String cardExpiration;
    protected String cardCVV;

    //getters

    public String getAddress() {
        return address;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String getCardExpiration(){
        return cardExpiration;
    }

    public String getCardCVV(){
        return cardCVV;
    }

    //setters


    public void setAddress(String address) {
        this.address = address;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

}
