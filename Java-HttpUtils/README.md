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

Nota: se o tipo do resultado não for especificado, o tipo padrão será `LinkedTreeMap<String, Object`, provido pelo Gson.

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

