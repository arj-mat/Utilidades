# Java Http Utils

### Introdução

Biblioteca para requisições Http que consomem resultados JSON.

Exemplos: https://github.com/arj-mat/Utilidades/tree/master/Java-HttpUtils/samples

### Dependências

- Lombok
- Gson

### Documentação

#### Objeto HttpResponseEntity

Retornado ao realizar uma requisição Http.

Nota: se o tipo do resultado não for especificado, o tipo padrão será LinkedTreeMap&lt;String Object>, provido pelo Gson.

| Propriedade | Tipo                                                        |
| :---------- | ----------------------------------------------------------- |
| data        | Optional&lt;TipoResultado>                                     |
| status      | Integer                                                     |
| headers     | Map&lt;String String>                                          |
| toString()  | Função que serializa o objeto data (se presente) como JSON. |

#### Método GET

HttpUtils.get(String url)

HttpUtils.get(String url, Map&lt;String String> headers)

HttpUtils.&lt;TipoResultado>get(TipoResultado.*class*, String url)

HttpUtils.&lt;TipoResultado>get(TipoResultado.*class*, String url, Map&lt;String String> headers)

#### Método POST

HttpUtils.&lt;TipoResultado>postEncodedForm(TipoResultado.*class*, String url, Map&lt;String String> body)

HttpUtils.&lt;TipoResultado>postEncodedForm(TipoResultado.*class*, String url, Map&lt;String String> body,  Map&lt;String String> headers)

HttpUtils.&lt;TipoResultado>postJSON(TipoResultado.*class*, String url, Object body)

HttpUtils.&lt;TipoResultado>postJSON(TipoResultado.*class*, String url, Object body,  Map&lt;String String> headers)

#### Método PUT

HttpUtils.putEncodedForm(String url, Map&lt;String String> body)

HttpUtils.putJSON(String url, Object body)

