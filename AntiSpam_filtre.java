import java.util.Scanner;

public class AntiSpam_filtre {

    public static int NB_APP_SPAM=200;
    public static int NB_APP_HAM=200;
    public static int NB_TEST_SPAM=500;
    public static int NB_TEST_HAM=500;
    public static String DIC="DATA/dictionnaire1000en.txt";
    public static String LIEN_BASE_APP="DATA/train-data";
    public static String LIEN_BASE_TEST="DATA/test-data";

    public static void main(String args[]){

        if(args.length>=3){
            LIEN_BASE_TEST=args[0];
            NB_TEST_SPAM=Integer.valueOf(args[1]);
            NB_TEST_HAM=Integer.valueOf(args[2]);
        }

        Scanner lecteur = new Scanner(System.in);
        System.out.print("Size of SPAM training data-set ?");
        NB_APP_SPAM =lecteur.nextInt();
        System.out.print("Size of HAM training data-set ?");
        NB_APP_HAM =lecteur.nextInt();

        DictionaryBuilder d=new DictionaryBuilder();
        d.charger(DIC);

        Naif_Bayes_Classifier _naif_bayesClassifier =new Naif_Bayes_Classifier(d);
        if(args.length==4) _naif_bayesClassifier.debutmsg=Integer.valueOf(args[3]);
        _naif_bayesClassifier.apprentissage(LIEN_BASE_APP,NB_APP_SPAM,NB_APP_HAM);
        _naif_bayesClassifier.test(LIEN_BASE_TEST,NB_TEST_SPAM,NB_TEST_HAM);



    }

}
