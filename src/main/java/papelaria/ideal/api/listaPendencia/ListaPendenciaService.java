package papelaria.ideal.api.listaPendencia;

import org.springframework.stereotype.Service;

@Service
public class ListaPendenciaService {

	public void cadastrar(DadosCadastroListaPendencia dados) {
		System.out.println("\n\n");
		System.out.println(dados);
		System.out.println("lista cadastrada");
	}

}
