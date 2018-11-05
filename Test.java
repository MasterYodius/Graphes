import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.security.auth.DestroyFailedException;

public class Test {
	public static void main(String[] args) {
		String s;
		System.out.println("Quelle graphe voulez vous tester(Saisissez le numéro du graphe)? Saisissez stop pour arrêter");
		Scanner scanner = new Scanner(System.in);
		s = scanner.next();
		
		while(!s.equals("stop")) {
		String choix = "graphes/Graphe"+s+".txt";

		Scanner sc; try {
		sc = new Scanner(new File(choix));
		
		int nbS = Integer.parseInt(sc.nextLine());
		int nbA = Integer.parseInt(sc.nextLine());
		//System.out.println(Integer.parseInt(sc.next()));
		
		MatriceAdj m = new MatriceAdj(nbS);
		//m.setVal(3, 2, 10);
		//m.afficherValeurs();
		
		
		while(sc.hasNextLine()) {
			//System.out.println(Integer.parseInt(sc.next()));
			int ligne = Integer.parseInt(sc.next());
			int colonne = Integer.parseInt(sc.next());
			int val = Integer.parseInt(sc.next());
			
			//System.out.println(ligne+" "+colonne+" "+val);
			
			
			
			m.setArc(ligne, colonne);
			m.setVal(ligne, colonne, val);
		}
		//m.afficheM();
		//System.out.println("");
		//m.afficherValeurs();
		//System.out.println("");
		
		MatriceAdj copie = new MatriceAdj(m);
		
		copie.afficheM();
		System.out.println("");
		copie.afficherValeurs();
		detectionCircuit(copie);
		//copie.afficherValeurs();
		//copie = supprimerSommet(copie, 2);
		//copie.afficheM();
		
		
		
		/*for(int i = 0; i<nbS; i++) {
			for(int j = 0;j< nbS;j++) {
				System.out.print(m.getval(i, j)+" ");
			}
			System.out.println("");
		}
		*/
		/*while(sc.hasNextLine()) {
			
		}
		
		System.out.println(nbS);
		System.out.println(nbA);*/

		//while(sc.hasNext())

		//System.out.println(sc.nextLine());
		
		

		} catch (FileNotFoundException e) { e.printStackTrace(); System.out.println(e.getMessage());
		}
		System.out.println("Continuer? Si oui saisissez le numéro du prochain graphe, sinon saisissez stop.");
		s = scanner.next();
		}
		scanner.close();
		
		}
	
	public static boolean detectionCircuit(MatriceAdj m) {
		boolean res = false;
		//int[] predess = new int[m.getNbSommet()];
		int ancien = m.getNbSommet();
		
		
		while(m.getNbSommet() > 0 && res == false) {
			int[] predess = new int[m.getNbSommet()];
			for(int colonne = 0; colonne < m.getNbSommet(); colonne++) {
				int cpt = 0;
				for(int ligne = 0 ; ligne< m.getNbSommet(); ligne++) {
					if(m.getArc(ligne, colonne) == 1) {
						cpt+=1;
					}
					predess[colonne] = cpt;
				}
				
				
				
				
				
			}
			for(int aff = 0 ;aff<predess.length;aff++) {
				System.out.print(predess[aff]+ " ");
				
			}
			System.out.println("\n");
			for(int v = 0;v<predess.length;v++) {
				if(predess[v] == 0) {
					//System.out.println(m.getNbSommet());
					//System.out.println(predess[v]);
					m = supprimerSommet(m, v);
					m.afficheM();
					System.out.println("");
					break;
				}
			}
			if(m.getNbSommet() == ancien) {
				res = true;
			}
			else {
				ancien = m.getNbSommet();
			}
			
			
		}
			
			//System.out.println(predess[v]);
			/*m.afficheM();
			System.out.println("");
			m = supprimerSommet(m, 2);
			m.afficheM();
			System.out.println("");
			m = supprimerSommet(m, 3);*/
			/*for(int v:predess) {
				if(predess[v] == 0) {
					//System.out.println(m.getNbSommet());
					System.out.println(predess[v]);
					m = supprimerSommet(m, v);
					break;
				}
				
			}
			if(m.getNbSommet() == ancien) {
				res = false;
			}
			//m = supprimerSommet(m, 2);
			
			
			
			
			
			
			
		}
		System.out.println("matrice \n");
		m.afficheM();*/
		
		if(res == true) {
			System.out.println("Ce graphe a un circuit");
		}
		else {
			System.out.println("Ce graphe n'a pas de circuit");
		}
		
		m.afficherValeurs();
		return res;
		
		
	}
	
	
	public static MatriceAdj supprimerSommet(MatriceAdj m, int sommet) {
		MatriceAdj m2 = new MatriceAdj(m.getNbSommet()-1);
		int newLigne = 0;
		int newCol = 0;
		for(int ligne = 0; ligne < m.getNbSommet(); ligne ++) {
			if(ligne == sommet) {
				continue;
			}
			else {
				for(int colonne = 0; colonne < m.getNbSommet(); colonne++) {
					if(colonne == sommet) {
						continue;
					}
					else {
						//System.out.println("ligne "+ligne+" colonne "+colonne+" "+m.getArc(ligne, colonne));
						if(m.getArc(ligne, colonne) == 1) {
							m2.setArc(newLigne, newCol);
							m2.setVal(newLigne, newCol, m.getval(ligne, colonne));
						}
					}
					newCol+=1;
				}
				newCol=0;
			}
			newLigne+=1;
		}
		//m2.setNbS(m2.getNbSommet()-1);
		return m2;
	}

}
