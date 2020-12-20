import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Msg_Reader {

    private DictionaryBuilder dictionaryBuilder;
    private boolean ponctuation_opt=true;
    private boolean balise_supp=true;
    boolean supprim_entete=true;
    private static char[] ponctuation="#$%&‘'!()*+,-./:;<=>?@[\\]_{}^".toCharArray();

    Msg_Reader(DictionaryBuilder dictionaryBuilder){
        this.dictionaryBuilder = dictionaryBuilder;
    }

    boolean[] lire_message(String lien){
        String msg= fichertoString(lien);
        boolean X[] =message_vecteur(msg);
        return X;
    }



    boolean[] message_vecteur(String msg){

        boolean[] X=new boolean[dictionaryBuilder.get_taille()];
        for (int i=0;i<X.length;i++)X[i]=false;

        msg=msg.toLowerCase();
        if(balise_supp)
            msg=supprimer_balise(msg);
        if(ponctuation_opt)
            msg=supprimer_ponctuation(msg);

        String[]motes =msg.split(" ");
        int ind;
        for(String mote : motes) {
            ind= dictionaryBuilder.index_mote(mote);
            if ( ind != -1) {
              X[ind]=true;
            }
        }

        return X;
    }

    String fichertoString(String lien){
        String line = null;
        String msg="";
        boolean debut=!supprim_entete;
        try {
            FileReader fileReader =
                    new FileReader(lien);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if(line.equals(""))
                    debut=true;
                if(debut)
                msg+=line;
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("erreur d'ouverture fichier" +lien);
        }
        catch(IOException ex) {
            System.out.println("erreur lecture fichier" +lien);
        }

        return msg;
    }


    String supprimer_ponctuation(String s){
     String msg_sans_p="";
     boolean ver;
     for (char c :s.toCharArray()){
         ver=false;
         for(char ponc :ponctuation){
             ver=(c==ponc);
             if(ver)break;
         }
         if(!ver)
             msg_sans_p+=c;
     }
     return msg_sans_p;
    }

    public  String supprimer_balise(String s) {
        String res="";
        boolean ver=false;
        for(char c : s.toCharArray()){
            if(c=='<')ver=true;
            if (!ver)res+=c;
            if(c=='>')ver=false;
        }
        return res;
    }

}
