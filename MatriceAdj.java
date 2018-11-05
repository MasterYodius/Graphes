import java.util.Collections;
import java.util.stream.Collectors;

public class MatriceAdj {
	private int nbsommets;
	private int[][] matrice;
	private int[][] valeurs;
	
	private static final int NULL = -99999999; 
	
	public MatriceAdj(int nbs) {
		this.nbsommets = nbs;
		this.matrice = new int[nbs][nbs];
		this.valeurs = new int[nbs][nbs];
		
		for(int i = 0; i < nbs; i++) {
			for(int j = 0; j < nbs; j++) {
				this.matrice[i][j] = 0;
			}
		}
		
		for(int i = 0; i < nbs; i++) {
			for(int j = 0; j < nbs; j++) {
				this.valeurs[i][j] = NULL;
			}
		}
		
		
	}
	
	public MatriceAdj(MatriceAdj m1) {
		this.nbsommets = m1.getNbSommet();
		
		this.matrice = new int[this.nbsommets][this.nbsommets];
		this.valeurs = new int[this.nbsommets][this.nbsommets];
		
		for(int i = 0; i < this.nbsommets; i++) {
			for(int j = 0; j < this.nbsommets; j++) {
				
				this.matrice[i][j] = m1.getArc(i, j);
				//System.out.println(i +" "+j+" "+this.getArc(i, j)+" ");
			}
		}
		
		for(int i = 0; i < this.nbsommets; i++) {
			for(int j = 0; j < this.nbsommets; j++) {
				this.valeurs[i][j] = m1.getval(i, j);
			}
		}
	}
	
	public int getNbSommet() {
		return this.nbsommets;
	}
	
	public void setNbS(int n) {
		this.nbsommets = n;
	}
	
	public int getval(int i, int j) {
		return this.valeurs[i][j];
	}
	
	public void setVal(int i, int j, int v) {
		this.valeurs[i][j] = v;
	}
	
	public int getArc(int i, int j) {
		return this.matrice[i][j];
	}
	
	public void setArc(int i, int j) {
		this.matrice[i][j] = 1;
	}
	
	
	
	public void afficheM() {
		for(int i = 0; i<this.nbsommets; i++) {
			for(int j = 0;j< this.nbsommets;j++) {
				System.out.print(this.matrice[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	public void afficherValeurs() {
		
		
		for(int i = 0; i< this.nbsommets; i++) {
			for(int j = 0; j< this.nbsommets; j++) {
				if(this.getval(i, j) == NULL ) {
					System.out.print("*  ");
					
				}
				else {
					System.out.print(this.getval(i, j)+"  ");
					
					
				}
			}
			System.out.println("");
		}
	}

}
