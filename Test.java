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
		String choix = "graphes/"+s+"graphe.txt";

		Scanner sc; try {
		sc = new Scanner(new File(choix));
		
		int nbS = Integer.parseInt(sc.nextLine());
		int nbA = Integer.parseInt(sc.nextLine());
		System.out.println("Lecture sur fichier :");
		System.out.println("Nombre de sommets :" + nbS);
		System.out.println("Nombre d'arc :" + nbA);
		
		MatriceAdj m = new MatriceAdj(nbS);
		
		
		while(sc.hasNextLine()) {
			int ligne = Integer.parseInt(sc.next());
			int colonne = Integer.parseInt(sc.next());
			int val = Integer.parseInt(sc.next());
			
			System.out.println(ligne+" -> "+colonne+" = "+val);
			
			
			
			m.setArc(ligne, colonne);
			m.setVal(ligne, colonne, val);
		}
		
		
		System.out.println("Représentation sous forme matricielle :");
		System.out.println("Matrice d'adjacence (1 signifie qu'il y a un arc et 0 signifie qu'il n'y en a pas) :");
		m.afficheM();
		System.out.println("Valeurs des arcs :");
		m.afficherValeurs();
		System.out.println("\n");
		System.out.println("Détéction de circuit :");
		boolean circuit = detectionCircuit(m);
		if(circuit == false) {
				verifOrdo(m);
			}
		
		
		} catch (FileNotFoundException e) { e.printStackTrace(); System.out.println(e.getMessage());
		}
		System.out.println("Continuer? Si oui saisissez le numéro du prochain graphe, sinon saisissez stop.");
		s = scanner.next();
		}
		scanner.close();
		
		}
	
	public static boolean detectionCircuit(MatriceAdj m) {  //par suppression des points d'entrée
		boolean res = false;
	
		int ancien = m.getNbSommet();		//pour garder le nombre de sommet avant suppression
		
		
		while(m.getNbSommet() > 0 && res == false) {			//Tant qu'il reste des sommets et des points d'entrée
			int[] predess = new int[m.getNbSommet()];			//cette boucle sert à avoir le nombre de prédécésseur de chaque sommet pour détécter les points d'entrée
			for(int colonne = 0; colonne < m.getNbSommet(); colonne++) {
				int cpt = 0;
				for(int ligne = 0 ; ligne< m.getNbSommet(); ligne++) {
					if(m.getArc(ligne, colonne) == 1) {
						cpt+=1;
					}
					predess[colonne] = cpt;
				}
				
			}
		
			for(int v = 0;v<predess.length;v++) {
				if(predess[v] == 0) {	//si un sommet n'a pas de prédécésseurs
					m = supprimerSommet(m, v);	//on supprime le sommet grâce à une méthode défini plus bas
					System.out.println("\n"+ "Suppression d'un point d'entrée");
					m.afficheM();
					
					break;
				}
			}
			if(m.getNbSommet() == ancien) {		//si on a le même nombre de sommet après suppression alors il n'y a plus de point d'entrée
				res = true;
				System.out.println("Il n'y a plus de points d'entrée");
			}
			else {
				ancien = m.getNbSommet();
			}
			
			
		}
		
		if(res == true) {
			System.out.println("Ce graphe a un circuit");
		}
		else {
			System.out.println("Ce graphe n'a pas de circuit");
		}
		return res;
	}
	
	
	public static MatriceAdj supprimerSommet(MatriceAdj m, int sommet) {
		MatriceAdj m2 = new MatriceAdj(m.getNbSommet()-1);	//on crée une nouvelle matrice avec un sommet en moins, et avec tous les arcs à 0
		int newLigne = 0;
		int newCol = 0;
		//Nous allons copier les valeurs de l'ancien graphe sauf celles qui se trouvent sur la ligne et la colonne du sommet à supprimer
		for(int ligne = 0; ligne < m.getNbSommet(); ligne ++) {
			if(ligne == sommet) {	//si nous sommes sur la ligne du sommet à supprimer, on ne fait rien et on passe directement à la prochaine itération
				continue;
			}
			else {
				for(int colonne = 0; colonne < m.getNbSommet(); colonne++) {
					if(colonne == sommet) {		//si nous sommes sur la colonne du sommet à supprimer, on ne fait rien et on passe directement à la prochaine itération
						continue;
					}
					else {
						if(m.getArc(ligne, colonne) == 1) {		//si il y a un arc entre le sommet "ligne" et le sommet "colonne" on copie les valeurs de l'ancien graphe
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
		return m2;
	}
	
	public static void verifOrdo(MatriceAdj m) {
		if(unPointDentree(m) == true) {
			System.out.println("Il n'y a qu'un seul point d'entrée");
			if(valEntree(m) == true)
				System.out.println("La valeur de ses arcs incidents vers l'exterieur est 0");
			else
				System.out.println("La valeur de ses arcs incidents vers l'exterieur n'est pas 0");

		}
		else
			System.out.println("Il y a plus d'un point d'entrée");
		
		if(unPointdeSortie(m) == true)
			System.out.println("Il n'y a qu'un seul point de sortie");
		else
			System.out.println("Il y a plus d'un point de sortie");
		if(verifValIdentique(m) == true)
			System.out.println("Les valeurs de tous les arcs allant vers l'exterieur de chaque sommet sont identiques");
		else
			System.out.println("Les valeurs de tous les arcs allant vers l'exterieur de chaque sommet ne sont pas identiques");
		if(verifPasNegative(m) == true)
			System.out.println("Il n'y a aucune valeur strictement négative");
		else
			System.out.println("Il y a au moins une valeur négative");


		if(unPointDentree(m)== true && unPointdeSortie(m)== true &&  verifPasNegative(m) == true && verifValIdentique(m) == true && valEntree(m)==true) {
			System.out.println("C'est donc un graphe d'ordonnancement");
		}
		else
			System.out.println("Ce n'est donc pas un graphe d'ordonnancement");

	}
	
	
	public static boolean unPointDentree(MatriceAdj m) {
		int nbEntree = 0;
		boolean res = true;
		int predess;
		for(int colonne = 0; colonne < m.getNbSommet(); colonne++) {			//on regarde le nombre de prédécésseurs de chaque sommet
			predess = 0;
			for(int ligne = 0; ligne < m.getNbSommet(); ligne++) {
				if(m.getArc(ligne, colonne) == 1) {
					predess += 1;
				}
			}
			if(predess == 0) {				// si on trouve un sommet qui n'a pas de prédécésseurs, on incrémente nbEntree
				nbEntree += 1;
			}
			if(nbEntree > 1) {				//si on a plus d'une sortie , inutile de continuer, on sort de la boucle et on met res à false
				res = false;
				break;
			}
		}
		
		if(nbEntree == 1) {			
			res = true;
		}
		
		return res;
		
	}
	
	public static boolean unPointdeSortie(MatriceAdj m) {
		int nbSortie = 0;
		boolean res = true;
		int succ;
		for(int ligne = 0; ligne < m.getNbSommet(); ligne++) {		//on regarde le nombre de successeurs de chaque sommet
			succ = 0;
			for (int colonne = 0;colonne < m.getNbSommet(); colonne++) {
				if(m.getArc(ligne, colonne) == 1) {
					succ+=1;
				}
			}
			if(succ == 0) {			// si on trouve un sommet qui n'a pas de successeurs, on incrémente nbSortie
				nbSortie+=1;
			}
			if(nbSortie > 1) {		//si on a plus d'une sortie , inutile de continuer, on sort de la boucle et on met res à false
				
				res = false;
				break;
			}
		}
		
		if(nbSortie == 1) {
			
			res = true;
		}
		return res;
	}
	
	public static boolean verifValIdentique(MatriceAdj m) {
		boolean res = true;
		int premiereVal;
		
		for(int ligne = 0; ligne < m.getNbSommet(); ligne++) {
			premiereVal = m.getval(ligne, 0);
			for(int colonne = 1; colonne <m.getNbSommet(); colonne ++) {				//pour trouver la valeur du premier arc
				
				if(premiereVal == m.getNull() && m.getArc(ligne, colonne) == 1) {
					premiereVal = m.getval(ligne, colonne);
				}
				
				else if(premiereVal != m.getNull() && m.getArc(ligne, colonne) ==1) {			//Il faut comparer la valeur avec celle du premier arc
					if(m.getval(ligne, colonne) != premiereVal) {
						res = false;
						break;
					}
				}
				
			}
			if(res == false) {
				break;
			}
		}
		if(res == true) {
		}
		return res;
	}
	
	public static boolean valEntree(MatriceAdj m) {
		int predess = 0; 
		int sommetEntree = 0;
		boolean res = false;
		if(unPointDentree(m) == true && verifValIdentique(m) == true) {
			for(int colonne=0; colonne < m.getNbSommet(); colonne++) {
				for(int ligne=0; ligne < m.getNbSommet(); ligne++) {
					if(m.getArc(ligne, colonne) == 1) {
						predess += 1; 
					}
				}
				if(predess == 0) {
					sommetEntree = colonne;
					break;
				}
			}
			if(verifValEntree(m, sommetEntree) == true) {
				res = true;
			}
			else {
				res = false;
			}
		}
		return res;
	}
	
	
	public static boolean verifValEntree(MatriceAdj m,int sommet) {
		boolean res = true;
		for(int colonne = 0; colonne < m.getNbSommet(); colonne++) {
			if(m.getArc(sommet, colonne) == 1 && m.getval(sommet, colonne) != 0) {
				System.out.println("Le sommet a un arc avec une valeur différente de 0");
				res = false;
				break;
			}
		}
		return res;
	}
	
	public static boolean verifPasNegative(MatriceAdj m) {
		boolean res = true;
		for(int ligne = 0; ligne < m.getNbSommet(); ligne++) {
			for(int colonne = 0; colonne < m.getNbSommet(); colonne++) {
				if(m.getArc(ligne, colonne) == 1 && m.getval(ligne, colonne) < 0) {
					res = false;
					break;
				}
			}
			if(res == false) {
				break;
			}
		}
		return res;
	}
}
