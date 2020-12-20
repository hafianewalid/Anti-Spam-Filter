public class Mail_filtre {

    public static String DIC="DATA/dictionnaire1000en.txt";

    public static void main(String args[]){

        if(args.length==2){

            DictionaryBuilder d=new DictionaryBuilder();
            d.charger(DIC);

            Msg_Reader msg_reader =new Msg_Reader(d);
            boolean[] X = msg_reader.lire_message(args[1]);

            Naif_Bayes_Classifier classifieur =new Naif_Bayes_Classifier(d);
            classifieur.charger(args[0]+".txt");

            double[]proba =new double[2];
            boolean spam=classifieur.prediction(X,proba);

            if(spam)
                System.out.println(args[0] + " Message " + args[1] + " is un SPAM !");
            else
                System.out.println(args[0] + " Message " + args[1] + " is an HAM !");

            System.out.println(" With spam proba = " + proba[0]);
            System.out.println(" With ham proba = " + proba[1]);



        }


    }

}
