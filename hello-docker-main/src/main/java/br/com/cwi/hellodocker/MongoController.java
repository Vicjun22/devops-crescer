package br.com.cwi.hellodocker;

import static com.mongodb.client.model.Filters.eq;

import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

@RestController()
@RequestMapping("/mongo")
public class MongoController {
    @Value("${mongodb.uri}")
    private String mongoUri;

    @GetMapping("/buscar") 
    public String mongo() {        
        var documento = buscarDocumentoNoMongo();

        if (documento == null) {
            var id = criarDocumentoNoMongo();       
            return "Foi inserido um registro no banco com id " + id.toString();
        }
                
        return documento.toJson();
    }

    public BsonValue criarDocumentoNoMongo() {
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase("database_filmes");
            MongoCollection<Document> collection = database.getCollection("filmes");
            
            InsertOneResult resultado = collection.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("nome", "Pulp Fiction"));
            
            return resultado.getInsertedId();
        }
    }

    public Document buscarDocumentoNoMongo() {
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase("database_filmes");
            MongoCollection<Document> collection = database.getCollection("filmes");
            
            return collection.find(eq("nome", "Pulp Fiction")).first();
        }
    }
}
