import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GeradorPaginas {

    public static void main(String[] args) {
        String arquivoCsv = "arquivo.txt";
        String template = "template.md";
    }

    private static void lerCsv (String arquivoCsv, String template) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoCsv))){
            br.readLine();
            String linhaTcc;

            while ((linhaTcc = br.readLine()) != null) {
                String[] dadosPlanilha = linhaTcc.split(",");
                String nomeArquivo = gerarNomeArquivo(dadosPlanilha[5], dadosPlanilha[0]);
                String templateArquivo = gerarArquivoTemplate(template, dadosPlanilha);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String gerarNomeArquivo (String aluno, String semestre) {
        return semestre.replace("/", "-") + "-" + aluno.toLowerCase().replace(" ", "-") + ".md";
    }

    private static String gerarArquivoTemplate (String template, String[] dadosPlanilha) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(template))) {
            String linhaTemplate;

            while ((linhaTemplate = bufferedReader.readLine()) != null) {
                linhaTemplate = linhaTemplate.replace("$titulo$", dadosPlanilha[1].trim())
                                             .replace("$nome", dadosPlanilha[0].trim())
                                             .replace("$area$", dadosPlanilha[4].trim())
                                             .replace("$data$", dadosPlanilha[6].trim())
                                             .replace("$hora$", dadosPlanilha[7].trim())
                                             .replace("$local$", dadosPlanilha[8].trim())
                                             .replace("$orientador$", dadosPlanilha[2].trim())
                                             .replace("$membros$", dadosPlanilha[3].trim());

                sb.append(linhaTemplate).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
