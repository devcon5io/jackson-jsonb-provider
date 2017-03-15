# jackson-jsonb-provider
A Jackson2 based provider based for  Json binding API (JSR 367).

This is an alternative to the reference implementation (Yasson) which is 
is not yet available on Maven central.

Using this provider allows you write code against the standardize Json-binding API while using a 
proven and reliable JSON binding implementation (Jackson). This decouples your code from a specific
implementation and allows to switch implementations more easily (theoretically).

Apart from that, there are no advantages over using Jackson directly, actually you can't access all
of the features provided by Jackson through the binding API.

# Usage

## Dependencies
adding this to your project
 
    <dependency>
        <groupId>io.devcon5</groupId>
        <artifactId>jackson-jsonb-provider</artifactId>
        <version>1.0</version>
    </dependency>

Note that the `javax.json-api` and `javax.json.bind-api` are not provided by this provider
and must be present in the classpath. You may add both dependencies using

    <dependency>
        <groupId>javax.json</groupId>
        <artifactId>javax.json-api</artifactId>
        <version>1.0</version>
    </dependency>
    <dependency>
        <groupId>javax.json.bind</groupId>
        <artifactId>javax.json.bind-api</artifactId>
        <version>1.0.0-M1</version>
    </dependency>

## Code Examples
To create a `Jsonb` instance for serializing/deserialing your objects to and
from Json:

    JsonbBuilder builder = JsonbBuilder.create()
    Jsonb jsonb = builder.build();
    
To serisalize your object to json
    
    MyObject obj = ...;
    String json = jsonb.toJson(obj);
    
To deserialize json to object

    MyObject obj2 = jsonb.fromJson(json, MyObject.class);
    
## Configuration
This provider can be configured to a small extend:

- including `null` properties in serialization (default: off) 

        JsonbConfig config = new JsonbConfig().withNullValues(true);
        builder.withConfig(config);
        
        Jsonb jsonb = builder.build();
    
- including empty properties (i.e. Collections or arrays) in serialization (default: off) 

        JsonbConfig config = new JsonbConfig().setProperty(JacksonJsonbBuilder.INCLUDE_EMPTY_VALUES, true);
        builder.withConfig(config);
        
        Jsonb jsonb = builder.build();
