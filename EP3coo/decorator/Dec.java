package decorator;
import principal.Produto;
import principal.ProdutoPadrao;

public class Dec extends ProdutoPadrao implements Produto,RelFormat {
	Produto p;
	public int format_flags;
	public String cor;
	public Dec(int id, String descricao, String categoria, int qtdEstoque, double preco, int formatacao, String cor){
		super(id, descricao, categoria, qtdEstoque, preco);
		this.format_flags=formatacao;
		this.cor = cor;
		this.p= new ProdutoPadrao(id, descricao, categoria, qtdEstoque, preco);
	}
	
	@Override
	public String formataParaImpressao(){
		String ret= "<li>";
		
		ret+="<p style=\"color: " + cor + "\">";

		if((format_flags & FORMATO_ITALICO) > 0){

			ret+="<span style=\"font-style:italic\">";
		}

		if((format_flags & FORMATO_NEGRITO) > 0){

			ret+="<span style=\"font-weight:bold\">";
		} 
		
		ret+=p.formataParaImpressao();
	
		if((format_flags & FORMATO_NEGRITO) > 0){

			ret+="</span>";
		} 

		if((format_flags & FORMATO_ITALICO) > 0){

			ret+="</span>";
		}
		
		ret+="<p>";

		ret+="</li>";
		
		return ret;
	}

	
}