# Java Http Utils

### Introdução

Biblioteca de requisições Http síncronas, para enviar/obter objetos JSON utilizando Google Gson.

Adicionalmente, também permite o envio de requisições POST ou PUT com conteúdo transformado em formulário HTML (encoded form).

Exemplos: https://github.com/arj-mat/Utilidades/tree/master/Java-HttpUtils/samples

### Dependências

- Lombok
- Gson

### Documentação

#### Objeto HttpResponseEntity&lt;TipoResultado>

Este é o objeto sempre retornado ao realizar uma requisição Http.

Nota: se o tipo do resultado não for especificado, o tipo padrão será `LinkedTreeMap<String, Object`, provido pelo Gson.

Nota: se uma requisição falhar antes da conexão Http, o status será -1.

| Propriedade | Tipo                                                        |
| :---------- | ----------------------------------------------------------- |
| data        | `Optional<TipoResultado>`                                   |
| status      | `Integer`                                                   |
| headers     | `Map<String, String>`                                       |
| toString()  | Função que serializa o objeto data (se presente) como JSON. |

#### Método GET

```java
HttpUtils.get(String url)

HttpUtils.get(String url, Map<String, String> headers)

HttpUtils.<TipoResultado>get(TipoResultado.class, String url)

HttpUtils.<TipoResultado>get(TipoResultado.class, String url, <String, String> headers)
```

#### Método POST

```java
HttpUtils.<TipoResultado>postEncodedForm(TipoResultado.class, String url, Map<String, String> body)
    
HttpUtils.<TipoResultado>postEncodedForm(TipoResultado.class, String url, Map<String, String> body, Map<String, String> headers)
```

 ```java
 HttpUtils.<TipoResultado>postJSON(TipoResultado.class, String url, Object body)
 
 HttpUtils.<TipoResultado>postJSON(TipoResultado.class, String url, Object body, Map<String, String> headers)
 ```

#### Método PUT

```java
HttpUtils.putEncodedForm(String url, Map<String, String> body)
```

```java
HttpUtils.putJSON(String url, Object body)
```

