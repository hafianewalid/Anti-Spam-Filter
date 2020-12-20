import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DictionaryBuilder {

    private HashMap<String, Integer> dictionnaire;


    public void charger(String lien){
        dictionnaire=new HashMap<>();

        String line;
        int ind=0;

        try {

            FileReader fileReader =
                    new FileReader(lien);


            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null ) {
                if (line.length()>2) {
                    line=line.toLowerCase();
                    dictionnaire.put(line, ind);
                    ind++;
                }
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("erreur d'ouverture fichier" +lien);
        }
        catch(IOException ex) {
            System.out.println("erreur lecture fichier" +lien);
        }

    }

    public int index_mote(String mote){
           Integer ind= dictionnaire.get(mote);
           if(ind==null)return -1;
           return ind;
    }

    public int get_taille(){
        return dictionnaire.size()+1;
    }


}
