package com.impacta.ac1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.* 
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.impacta.ac1.ui.theme.AC1Theme
import java.util.Locale


class MainActivity : ComponentActivity() { //Esta é a classe de atividade principal do seu aplicativo. Ele herda da ComponentActivity, que é uma classe fornecida pelo Jetpack Compose.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { //Define o conteúdo da atividade como um Composable chamado AC1Theme
            AC1Theme {
                Surface( //Cria uma superfície que preenche toda a tela e define a cor de fundo com base no esquema de cores do Material Design.
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NumerologyCalculator() //Chama o Composable NumerologyCalculator(), que é onde a interface do usuário principal do aplicativo é construída.
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumerologyCalculator() { //NumerologyCalculator: Este é um Composable personalizado que representa a parte principal da IU do seu aplicativo.
    var name by remember { mutableStateOf("") } //São variáveis Composable que armazenam o nome inserido pelo usuário e o resultado do cálculo da numerologia.
    var result by remember { mutableStateOf("") } //O remember é usado para lembrar do estado das variáveis entre recomposições.
    Column( //Aqui conseguimos editar o visual
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField( // Um campo de texto onde o usuário pode inserir seu nome. O valor inserido é armazenado na variável name.
            value = name,
            onValueChange = { name = it },
            label = { Text("Digite seu nome e sobrenome:") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button( //Botão que usamos para chamar a função
            onClick = { //Quando o botão é clicado, este bloco de código é executado
                val isValidName = validateName(name)
                if (isValidName) { // Se o nome for válido
                    val numerologyValue = calculateNumerologyValue(name)
                    result = getNumerologyResult(numerologyValue)
                } else { // Se o nome não for válido
                    result = "Nome inválido! Digite um nome válido."
                }
            },
            modifier = Modifier.fillMaxWidth() // O botão exibe o texto "Calcular"
        ) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = result,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Seu texto aqui",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun validateName(name: String): Boolean {
    // Verifica se o nome inserido é válido.
    return name.length >= 3 // Deve ter pelo menos 3 caracteres no total
            && name.any { it.isLetter() || it != ' ' } // Não deve conter caracteres especiais
            && name.trim().contains(' ')
}


fun calculateNumerologyValue(name: String): Int { // Defina seu alfabeto e valores do alfabeto
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val alphabetValues = intArrayOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 7, 6, 8
    )
    // calcula o valor da numerologia com base no nome inserido
    val sanitizedName = name.uppercase(Locale.getDefault()).filter { it in alphabet }
    // A próxima seção do código é responsável por calcular o valor numerológico do nome.
    // O nome é primeiramente convertido para letras maiúsculas usando 'uppercase(Locale.getDefault())'
    // para garantir que não haja diferença entre letras maiúsculas e minúsculas na contagem.
    // Em seguida, é aplicado um filtro para manter apenas os caracteres que estão no alfabeto definido.
    var soma = sanitizedName.sumOf { alphabetValues[alphabet.indexOf(it)] }
    if (soma == 11 || soma == 22)
    {
        return soma
    }
    else
    {
        var aux = 0
        while(soma.toString().length > 1)
        {
            aux = 0
            for(c in soma.toString())
            {
                aux += c.toString().toInt()
            }
            soma = aux
        }
        return soma
    }
}
    // A função 'sumOf' é usada para calcular a soma dos valores correspondentes aos caracteres do nome
    // com base nos valores do alfabeto.

fun getNumerologyResult(somaRealNome: Int): String { //  é uma função que recebe a soma real do nome (o valor calculado pela função calculateNumerologyValue) e retorna uma explicação detalhada com base no valor da numerologia.
    return when (somaRealNome) {
        1 -> """É o Líder. Você veio para exercer a liderança e atrair seguidores. Tem o pensamento autocentrado, procurando a autopreservação. Deve desenvolver o seu EU INTERIOR, suas forças íntimas. Mantendo autoconfiança e determinação chegará longe. Mas precisará manter a humildade, pois a tendência em tornar-se egocêntrico é grande e isso lhe trará dissabores na vida. Sua independência e iniciativa servirão de inspiração a muitas pessoas. Pratique a sua habilidade em vencer e seja original, você veio para abrir os horizontes, por isso não tenha medo de ousar. Encare as situações com confiança e busque ser criativo.
        Atividade, pioneirismo, independência, invenção, força, ambição.
        Preguiça, medo, instabilidade, egoísmo, obstinação, tirania, dominação, rivalidade."""
        2 -> """É o Parceiro, aquele que propaga a paz. Você veio para cooperar, para pacificar os ambientes, para trazer a diplomacia. Tem sensibilidade para reconhecer os interesses opostos e consegue lidar com situações tensas com maestria.
        Você veio para trabalhar em sociedade, para apoiar líderes, por isso poderá até se tornar um embaixador. Você é aquela pessoa que dá sustento para o líder, aquela que faz com que as coisas fluam de forma harmoniosa e é ótima em trabalho de equipe. Poderá ser solicitada constantemente para resolver conflitos, o que pode te trazer um certo desgaste emocional. Porém mantenha a fé e a paciência, pois sua energia de amor irá ajudar a melhorar os ambientes em que estiver.
        Harmonia, serviço, diplomacia, consideração, atenção aos detalhes, paciência.
        timidez, supersensibilidade, negligência, dependência, covardia, crueldade, decepção, depressão por excesso de zelo."""
        3 -> """É o Comunicador, aquele que se expressa através das palavras ou da arte. Você veio para espalhar alegria. Será um bom escritor, ator ou qualquer profissão que o instigue a usar a imaginação e a autoexpressão.
        Busque o autoconhecimento e leve ao mundo o que aprendeu, pois, seus dons de comunicação o levarão ao sucesso. A tendência em assumir muitas responsabilidades e não dar conta é grande, por isso não desperdice energias e use seu tempo com inteligência para que possa ser bem-sucedido. Procure sua própria verdade e não se preocupe tanto com o que as pessoas pensam, principalmente relacionado às aparências.
        Alegria, bom humor, entusiasmo, otimismo, comunicação.
        Fofoca, pessimismo, extravagância, orgulho falso, ciúme, hipocrisia, avareza, fraude, intolerância."""
        4 -> """É o Construtor do mundo, aquele que sabe o valor do trabalho. Você veio para construir, para solidificar e inspirar confiança. Tem um senso prático e de organização, principalmente com as finanças. Gosta de planejar cada passo, tem objetivos claros e as metas o instigam a dar o seu melhor. É uma pessoa trabalhadora nata e, por isso, deve cuidar para não se tornar workaholic, que passa 24 horas ligado ao trabalho e nunca tira férias. Aprenda a medir o tempo, pois o ócio pode ajudar a ter mais criatividade. Você terá sucesso se trabalhar com finanças ou qualquer profissão que precise de um senso prático e planejamento.
                Seus pensamentos são sistemáticos e estáveis. Por isso, poderá ser um bom empresário, porém é importante não se cobrar tanto e também não exigir a perfeição das pessoas (tanto no trabalho, quanto nos demais ambientes). O sucesso virá, mas não da noite para o dia. Por isso cuidado com frustração quando as coisas não saírem exatamente como você planejou. O segredo é fazer a sua parte, mas permitir que o Universo também faça a Dele. Se estiver em densidade viverá problemas financeiros e de organização.
                confiabilidade, determinação, honestidade, lealdade, organização, economia.
        limitação, rigidez, rudeza, impaciência, autoritarismo, tirania familiar, desorganização financeira, ódio, violência, vulgaridade, crueldade, ciúme, avareza."""
	    5 -> """É o Espírito Livre, aquele que é independente e quebra padrões. Você veio para trazer a mudança, promover o progresso, quebrar paradigmas. Deve se adaptar às mudanças que sempre ocorrerão na sua vida, pessoas, lugares, momentos, conceitos e novos pontos de vista.
                Você tem a alma livre e deve levar a vida de forma leve, não se apegando a dogmas, ideias antigas e ultrapassadas. Você será aquele que levará a informação e promoverá o esclarecimento para as pessoas, pois tem habilidades de compreensão do que é abstrato, assim como o dom com as palavras e poderá gostar de escrever, lecionar ou vender. Deve experimentar o novo e alimentar seu espírito de liberdade através da arte, viagens, aventuras e momentos ao ar livre, em contato com a natureza para se energizar. Lembre-se que liberdade é diferente de rebeldia, busque questionar e encontre as respostas por si mesmo, mas não se torne aquele que questiona negando, por simples capricho do ego. Se tornar um rebelde sem causa, entregue a vícios seria um desperdício das suas potencialidades.
        liberdade, aventura, viagens, ousadia, progresso, versatilidade.
        irresponsabilidade, mutabilidade, desconsideração, adiamento, falta de comprometimento, perversão, sensualidade, deboche, drogas, vícios, malevolência."""
	    6 -> """É a Família, aquele que se dedica ao ambiente familiar e à comunidade. Você veio para servir, para dar amor, harmonizar os ambientes e ser um porto seguro para família. Tem capacidade de assumir grandes responsabilidades, é bondoso e preserva as tradições familiares. Tem talentos artísticos, gosta de conforto e beleza. Busca o matrimônio e formar um lar. Profissões ligadas ao serviço como médico, enfermeiro, professor, músico, ator, decorador de ambientes e etc, são indicações de sucesso. Gosta de receber as pessoas, sendo um bom anfitrião. Precisa de paixão para se sentir feliz, por isso deve procurar fazer o que ama. É importante cuidar com a tendência de querer cuidar de todo mundo e esquecer de si mesmo.
                Serviço, responsabilidade, domesticidade, bondade, família.
        Ansiedade, preocupação, intromissão, hábito de discussão, carência, tirania doméstica, cinismo, ciúme, presunção, chantagem."""
	    7 -> """É o Pensador, aquele que analisa tudo com cautela e consegue ver além das aparências. Você veio para desenvolver seus dons mentais, será um especialista, pois sua intuição é aguçada, com capacidade analítica que enxerga nas entrelinhas. Consegue separar o “joio do trigo” e poderá gostar de assuntos metafísicos, científicos e religiosos. Precisa de momentos de silêncio, para ficar a sós com seus pensamentos. A meditação o ajuda a encontrar inspiração. Sua mente é inventiva e curiosa, procura questionar e encontrar as respostas para questões profundas da vida. Muitos poderão considerá-lo estranho, principalmente pela sua necessidade de introspecção.
                Busque adquirir sabedoria, de forma neutra, para que não se torne um fanático ou então “dono da verdade”, principalmente em assuntos tabu. Saiba que suas palavras terão grande efeito nas pessoas, você sempre será procurado como conselheiro, pois seus conhecimentos são superiores e tem ligação direta com a espiritualidade. Permita-se conhecer os diferentes caminhos e desenvolva o seu EU interior, conectando-se com assuntos espirituais de forma livre. Desenvolva teorias, faça pesquisas e leve ao mundo seus conhecimentos singulares, mas contenha o seu desejo de manipular as situações. O fato de você conhecer mais sobre algo, não quer dizer que você é superior, viva com humildade, todos têm algo a nos ensinar.
                espiritualidade, introspecção, silêncio, sensatez, estudo.
        sarcasmo, frieza, nervosismo, humilhação, melancolia, egocentrismo, desonestidade, malícia, incredulidade, repressão, manipulação, julgamento."""
	    8 -> """É o Realizador, aquele que busca o sucesso por seus próprios esforços. Você veio para desenvolver a satisfação pessoal em realizar, buscar o sucesso, mas trabalhar em prol de um bem maior e não só pelo dinheiro. Você terá oportunidades de grandes negócios, prosperidade, sucesso e riqueza, porém isso dependerá do seu esforço em equilibrar a vida material e espiritual. As forças do 8 instigam o desapego (material/emocional) e uma fé inabalável, permita-se viver isso, pois a grande armadilha dessa vibração é se tornar apegado, viver preocupado com bens materiais e com medo de não ser bem-sucedido. Faça tudo de maneira correta, não tente “pular degraus” para o sucesso, pois o 8 é o número da justiça e você sempre será medido por ela. Procure associações com pessoas influentes e talentosas, você é um grande líder. Use sua coragem e força para vencer. Poderá gostar de esportes e será excelente em tudo o que se determinar a fazer.
        desapego, fé, esforço, coragem, habilidade executiva, liderança, autoridade.
        impaciência, intolerância, tensão, intriga, crueldade, vingança, injustiça, falsidade, opressão, vícios."""
	    9 -> """É o Generoso, aquele que serve aos outros sem esperar nada em troca. Você veio para ser o “irmão mais velho” aquele que aconselha com sabedoria e sabe respeitar as diferenças. Sua compaixão faz com que saiba se colocar no lugar do outro como ninguém, por isso sempre estará em ambientes que poderá ajudar as pessoas de alguma forma. Poderá enfrentar provas, pois veio sobre essa vibração para vencer suas sombras interiores. Busque o perdão e também o autoconhecimento, quanto mais estiver ativo abrindo-se para o novo, mais se sentirá feliz.
                A sua armadilha é tornar-se obsoleto e apegado ao passado. A leveza e sabedoria com que leva a vida, inspirará as pessoas. Seu desejo profundo é melhorar o mundo, por isso estará envolvido com muitas pessoas, não se limite a um pequeno grupo de amigos. Fazer trabalho voluntário pode amenizar o desejo de estar sempre fazendo tudo por todos e se colocar em último lugar. Lembre-se que só conseguimos ajudar verdadeiramente as pessoas quando nos ajudarmos primeiro. Tome cuidado com a tendência a “livrar-se” dos bens materiais, gastando mais do que ganha, ou doando tudo e passando maus bocados. Talvez lá no fundo não se sinta merecedor, já que há tantas injustiças no mundo.
        altruísmo, generosidade, amor universal, serviço, simpatia.
        dissipação, egoísmo, emocionalismo, autodegradação, indiscrição, amargura, falsidade, vícios, insociabilidade, imoralidade."""
	    11 -> """É o Líder Servidor, aquele que abre caminhos. Nunca reduzimos o 11 a 2, pois o 11 é líder, o 2 é o cooperador. Porém por ser uma vibração de número mestre, tem a habilidade de ser líder e ao mesmo tempo cooperador. Você veio para colocar em prática seus sonhos idealistas e ousados. Estará cercado de pessoas e lhes servirá como líder, guiando para os caminhos de luz e amor. Seu padrão de comportamento deverá ser acima da média. É provável que passe desafios, principalmente em relação a cobrança das pessoas, que esperarão de você uma conduta exemplar, não tolerando erros. Faça as pazes com essas situações e não se cobre tanto, seu campo magnético é de muita luz e isso atrairá sombras. Se mantiver a fé, será amparado e auxiliado pela espiritualidade. Sua intuição é aguçada por isso tome cuidado com as palavras, elas têm sentença de vida e de morte, você tem o dom da profecia. Procure se desenvolver espiritualmente de forma livre, para que possa se conectar com o seu Eu Superior. Em alguns momentos pode	rá se sentir sobrecarregado pelo fardo da sua missão, estando limitado, viverá todas as negatividades do número 2 (leia as observações desse número), lembre-se que poderá escolher o que deseja viver.
                Intuição, fé, inspiração, invenção, revelação, idealismo.
        fanatismo, falta de objetivos, carência, medo, desonestidade, avareza, degradação, perversidade, deterioração."""
	    22 -> """É o Mestre Construtor, aquele que atinge grandes massas. Você veio para liderar e assumir grandes responsabilidades. Busca o poder e a fortuna, se envolve em grandes projetos e poderá ter oportunidades de atingir influência mundial. Essa vibração de número mestre lhe confere grande poder de construção. Se sua intenção for de melhorar a vida das pessoas, obterá sucesso e atingirá nações construindo pontes, hospitais e outras obras para o mundo. Tem capacidade política, será recompensado na medida em que servir com ética. Se estiver em densidade, pode estar vivendo as negatividades do número 4 (leia as observações desse número), limitando sua potencialidade. Seja quem você nasceu para ser e não desperdice seus talentos.
        poder, liderança, influência internacional, idealismo, praticidade, expansão.
        indiferença, complexo de inferioridade, reprovação, perversidade, imprudência, incapacidade, manipulação."""
        else -> "Número não reconhecido."
    }
}

@Preview(showBackground = true)
@Composable
fun NumerologyCalculatorPreview() {
    AC1Theme {
        NumerologyCalculator()
    }

}
