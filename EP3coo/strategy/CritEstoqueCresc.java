package strategy;
import principal.Produto;

public class CritEstoqueCresc implements Crit,UsaSortAlg{
public int algoritmo;

	public Produto[] produtos;
	public CritEstoqueCresc(Produto[] produtos, int algoritmo){
		this.produtos=produtos;
		this.algoritmo=algoritmo;
	
	}
public int particiona(int ini, int fim){

		Produto x = produtos[ini];
		int i = (ini - 1);
		int j = (fim + 1);

		while(true){

			
				do{ 
					j--;

				} while(produtos[j].getQtdEstoque() > x.getQtdEstoque());
			
				do{
					i++;

				} while(produtos[i].getQtdEstoque() < x.getQtdEstoque());

			
			if(i < j){
				Produto temp = produtos[i];
				produtos[i] = produtos[j]; 				
				produtos[j] = temp;
			}
			else return j;
		}
	}

	public void ordena(int ini, int fim){

		if(algoritmo == ALG_INSERTIONSORT){

			for(int i = ini; i <= fim; i++){

				Produto x = produtos[i];				
				int j = (i - 1);

				while(j >= ini){

					
						if(x.getQtdEstoque() < produtos[j].getQtdEstoque()){
			
							produtos[j + 1] = produtos[j];
							j--;
						}
						else break;
					}

				produtos[j + 1] = x;
			}
		}
		else if(algoritmo == ALG_QUICKSORT){

			if(ini < fim) {

				int q = particiona(ini, fim);
				
				ordena(ini, q);
				ordena(q + 1, fim);
			}
		}
		else {
			throw new RuntimeException("Algoritmo invalido!");
		}
	}
	
}