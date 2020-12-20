import java.io.*;

public class Naif_Bayes_Classifier {

    int debutmsg=0;
    private static double epsilon=1;
    int nSPAM[],nHAM[];
    double bSPAM[],bHAM[];
    double P_spam,P_ham;
    DictionaryBuilder dictionaryBuilder;
    Msg_Reader msg_reader;
    int nbspam,nbham;


    Naif_Bayes_Classifier(DictionaryBuilder dictionaryBuilder){
        this.dictionaryBuilder = dictionaryBuilder;
        msg_reader =new Msg_Reader(this.dictionaryBuilder);
    }


    private void calcule_b(double[]b,int[]n,String lien,int nb){
        boolean[] X;


        for(int i=0;i<nb;i++){
            X= msg_reader.lire_message(lien+i+".txt");
            for (int j=0;j<X.length;j++)
                if(X[j]) {
                    n[j]++;
                }
        }

        double d=2*epsilon+nb;
        for(int i=0;i<b.length;i++){
            b[i]=(epsilon+n[i])/d;
        }

    }

    void apprentissage(String lien_base,int nbspam,int nbham){
        bSPAM=new double[dictionaryBuilder.get_taille()];
        bHAM=new double[dictionaryBuilder.get_taille()];
        nSPAM=new int[dictionaryBuilder.get_taille()];
        nHAM=new int[dictionaryBuilder.get_taille()];

        for(int i=0;i<bSPAM.length;i++){
            nSPAM[i]=0;
            nHAM[i]=0;
        }

        calcule_b(bSPAM,nSPAM,lien_base+"/spam/",nbspam);
        calcule_b(bHAM,nHAM,lien_base+"/ham/",nbham);

        double totale=nbspam+nbham;
        P_spam=nbspam/totale;
        P_ham=nbham/totale;

        this.nbspam=nbspam;
        this.nbham=nbham;

    }




    boolean prediction(boolean[] X,double[] proba){
        double Z_spam=Math.log(P_spam);
        double Z_ham=Math.log(P_ham);

        for(int i=0;i<X.length;i++)
            if(X[i]){
                Z_spam+=Math.log(bSPAM[i]);
                Z_ham+=Math.log(bHAM[i]);
            }else {
                Z_spam+=Math.log(1-bSPAM[i]);
                Z_ham+=Math.log(1-bHAM[i]);
            }

        proba[0]= 1.0/(1.0+Math.exp(Z_ham-Z_spam));//proba_spam
        proba[1]= 1.0/(1.0+Math.exp(Z_spam-Z_ham));//proba_hpam


        return Z_spam>=Z_ham;

    }

    void test(String lien_base,int nbspam,int nbham){
        boolean[]X;
        int spam_err=0;
        int ham_err=0;
        int spam_pred=0;
        int ham_pred=0;

        boolean predic;
        double proba[]=new double[2];

        System.out.println("Test :");

        for (int i=debutmsg;i<debutmsg+nbspam;i++) {
            X = msg_reader.lire_message(lien_base + "/spam/" + i + ".txt");
            predic=prediction(X,proba);

            System.out.println("SPAM num "+i+" : P(Y=SPAM | X=x) = "+proba[0]+", P(Y=HAM | X=x) ="+proba[1]);
            if(predic) {
                System.out.println("=> predicted as a SPAM");
                spam_pred++;
            }else{
                System.out.println("=> predicted as a HAM    *** wrong ***");
                spam_err++;
                ham_pred++;
            }
        }

        for (int i=debutmsg;i<nbham+debutmsg;i++) {
            X = msg_reader.lire_message(lien_base + "/ham/" + i + ".txt");
            predic=prediction(X,proba);

            System.out.println("HAM num "+i+" : P(Y=SPAM | X=x) = "+proba[0]+", P(Y=HAM | X=x) ="+proba[1]);
            if(!predic) {
                System.out.println("=> predicted as a  HAM");
                ham_pred++;
            }else{
                System.out.println("=> predicted as a  SPAM      *** wrong ***");
                ham_err++;
                spam_pred++;
            }
        }

        System.out.println("Error on "+nbspam+" test-SPAM :"
                +(int)((((double)spam_err/nbspam))*100)+" %");

        System.out.println("Error on "+nbham+" test-HAM        :"
                +(int)((((double)ham_err)/nbham)*100)+" %");

        System.out.println("Global error on "+(nbspam+nbham)+" test-mails  :"
                +(int)((((double)(spam_err+ham_err))/(nbspam+nbham))*100)+" %");

        System.out.println();
        System.out.println();
        System.out.println("///////////////////////////////////////////////////////////////////////");
        System.out.println();
        System.out.println();
        System.out.println("-------------------------------Matrice de confusion--------------------");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("                    |       actual_HAM       |       actual_SPAM       ");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(" predicted   HAM   |        TN="+(nbham-ham_err)+"         |        FN="+spam_err);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(" predicted   SPAM  |        FP="+ham_err+"         |       TP="+(nbspam-spam_err));
        System.out.println("-----------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println("///////////////////////////////////////////////////////////////////////");
        System.out.println();
        System.out.println();
        float hamprecision=((float) (nbham-ham_err))/ham_pred;
        float spamprecision=((float) (nbspam-spam_err))/spam_pred;
        float hamrappel=((float)(nbham-ham_err))/nbham;
        float spamrappel=((float)(nbspam-spam_err))/nbspam;
        float hamF=(hamprecision*hamrappel)/(hamprecision+hamrappel);
        float spamF=(spamprecision*spamrappel)/(spamprecision+spamrappel);
        System.out.println("---------------------Rappel &  Precision &  F-mesure--------------------");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("                    |      class _ HAM       |      class _ SPAM       ");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(" Precision          |           "+hamprecision+"         |           "+spamprecision);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(" Rappel             |           "+hamrappel+"         |          "+spamrappel);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(" F-mesure           |           "+hamF+"         |          "+spamF);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println("                 Precision average = "+(hamprecision+spamprecision)/2);
        System.out.println("                 Rappel average   = "+(hamrappel+spamrappel)/2);
        System.out.println("                 F-mesure  average = "+(hamF+spamF)/2);
    }

    void sauvegarder(String lien){
        BufferedWriter writer = null;
        String s;
        try {
            writer = new BufferedWriter(new FileWriter(lien+".txt"));
            writer.write(nbspam+" "+nbham+"\n");

            s="";
            for(int d:nSPAM)
                s+=d+" ";
            writer.write(s+"\n");

            s="";
            for(int d:nHAM)
                s+=d+" ";
            writer.write(s);

            writer.close();

        } catch (IOException e) {
        }
    }

    void charger(String lien){
        String line ;
        String[] donnee;
        try {
            FileReader fileReader =
                    new FileReader(lien);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            donnee=line.split(" ");
            nbspam=Integer.valueOf(donnee[0]);
            nbham=Integer.valueOf(donnee[1]);

            double totale=nbspam+nbham;
            P_spam=nbspam/totale;
            P_ham=nbham/totale;

            line =bufferedReader.readLine();
            donnee=line.split(" ");
            nSPAM=new int[donnee.length];
            bSPAM=new double[donnee.length];
            double d=2*epsilon+nbspam;
            for(int i=0;i<donnee.length;i++) {
                nSPAM[i] = Integer.valueOf(donnee[i]);
                bSPAM[i] =(nSPAM[i]+epsilon)/d;
            }

            line =bufferedReader.readLine();
            donnee=line.split(" ");
            nHAM=new int[donnee.length];
            bHAM=new double[donnee.length];
            d=2*epsilon+nbham;
            for(int i=0;i<donnee.length;i++) {
                nHAM[i] = Integer.valueOf(donnee[i]);
                bHAM[i] =(nHAM[i]+epsilon)/ d;
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

    void apprend_enligne(boolean[] X,boolean classe){
        double d=2*epsilon;
        if(classe){
            nbspam++;
            d+=nbspam;
            for(int i=0;i<X.length;i++){
                if(X[i]){
                    nSPAM[i]++;
                    bSPAM[i]=(nSPAM[i]+epsilon)/d;
                }
            }
        }else{
            nbham++;
            d+=nbham;
            for(int i=0;i<X.length;i++){
                if(X[i]){
                    nHAM[i]++;
                    bHAM[i]=(nHAM[i]+epsilon)/d;
                }
            }
        }

        double totale=nbspam+nbham;
        P_spam=nbspam/totale;
        P_ham=nbham/totale;

    }

}