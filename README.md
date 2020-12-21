# Anti Spam Filter

Tor train and test an anti spam filter use the following comand with the numbers of spam an ham will be used in training and testing.

`$ java AntiSpam_filtre DATA/test-data 100 200`

The results should look like the following :

```
Size of SPAM training data-set ? 200
Size of HAM training data-set ? 200
Test :
SPAM num 0 : P(Y=SPAM | X=x) = 0.9998238441564001, P(Y=HAM | X=x) =1.7615584359996576E-4
=> predicted as a SPAM
SPAM num 1 : P(Y=SPAM | X=x) = 0.42300796760454273, P(Y=HAM | X=x) =0.5769920323954572
.
.
.
HAM num 198 : P(Y=SPAM | X=x) = 0.8330597342029598, P(Y=HAM | X=x) =0.16694026579704027
=> predicted as a  SPAM      *** wrong ***
HAM num 199 : P(Y=SPAM | X=x) = 7.657145424532268E-23, P(Y=HAM | X=x) =1.0
=> predicted as a  HAM
Error on 100 test-SPAM :9 %
Error on 200 test-HAM        :7 %
Global error on 300 test-mails  :8 %


///////////////////////////////////////////////////////////////////////


-------------------------------Matrice de confusion--------------------
-----------------------------------------------------------------------
                    |       actual_HAM       |       actual_SPAM       
-----------------------------------------------------------------------
 predicted   HAM   |        TN=185         |        FN=9
-----------------------------------------------------------------------
 predicted   SPAM  |        FP=15         |       TP=91
-----------------------------------------------------------------------


///////////////////////////////////////////////////////////////////////


---------------------Rappel &  Precision &  F-mesure--------------------
-----------------------------------------------------------------------
                    |      class _ HAM       |      class _ SPAM       
-----------------------------------------------------------------------
 Precision          |           0.9536083         |           0.8584906
-----------------------------------------------------------------------
 Rappel             |           0.925         |          0.91
-----------------------------------------------------------------------
 F-mesure           |           0.46954316         |          0.4417476
-----------------------------------------------------------------------


                 Precision average = 0.90604943
                 Rappel average   = 0.9175
                 F-mesure  average = 0.45564538
```

For train and save the an anti spam model you can use the following line command :

`$ java Learning  DATA/train-data 500 1000`

To check a message using an anti spam model you can use the following command :

`$ java Mail_filtre model msg.txt`

```
The results should look like :

model Message msg.txt is un SPAM !
 With spam proba = 0.9999998112320735
 With ham proba = 1.8876792656914667E-7
```

To use an anti spam model for an online learning run the following command :

`$  java Online_Learning model msg.txt SPAM`
