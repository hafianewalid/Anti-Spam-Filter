public class Online_Learning {

    public static String DIC="DATA/dictionnaire1000en.txt";

    public static void main(String args[]){

        if(args.length==3){

            DictionaryBuilder d=new DictionaryBuilder();
            d.charger(DIC);

            Msg_Reader msg_reader =new Msg_Reader(d);
            boolean[] X = msg_reader.lire_message(args[1]);

            Naif_Bayes_Classifier classifieur =new Naif_Bayes_Classifier(d);
            classifieur.charger(args[0]+".txt");

            boolean spam=args[2].toLowerCase().equals("spam");

            classifieur.apprend_enligne(X,spam);
            classifieur.sauvegarder(args[0]);

        }


    }
}
