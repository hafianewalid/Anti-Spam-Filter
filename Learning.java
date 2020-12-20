public class Learning {

    public static int NB_APP_SPAM=200;
    public static int NB_APP_HAM=200;
    public static String DIC="DATA/dictionnaire1000en.txt";
    public static String LIEN_BASE_APP="DATA/train-data";

    public static void main(String args[]){
        DictionaryBuilder d=new DictionaryBuilder();
        d.charger(DIC);

        if(args.length==4){

        LIEN_BASE_APP=args[1];
        NB_APP_SPAM=Integer.valueOf(args[2]);
        NB_APP_HAM=Integer.valueOf(args[3]);


        Naif_Bayes_Classifier _naif_bayesClassifier =new Naif_Bayes_Classifier(d);
        _naif_bayesClassifier.apprentissage(LIEN_BASE_APP,NB_APP_SPAM,NB_APP_HAM);
        _naif_bayesClassifier.sauvegarder(args[0]);

        Naif_Bayes_Classifier _naif_bayes_Classifier_ch =new Naif_Bayes_Classifier(d);
        _naif_bayes_Classifier_ch.charger(args[0]+".txt");

        }

    }
}
