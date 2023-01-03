package projeto.seguranca.token;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import projeto.seguranca.modelo.Autenticacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Seguranca {

    private  AesKey privateKey;
    private  AesKey publicKey;
    public Seguranca() throws IOException {
        privateKey = new AesKey(new BufferedReader(new FileReader("Caminho para sua chave privara .txt")).readLine().getBytes());
        publicKey = new AesKey(new BufferedReader(new FileReader("Caminho para sua chave publica .txt")).readLine().getBytes());
    }

    public String criarToken(Autenticacao autenticacao) {
        return criarToken(autenticacao, 525600);
    }

    public String criarToken(Autenticacao autenticacao, Integer tempo_expiracao_minutos) {
        try {
            //Informações do token
            // Create the Claims, which will be the content of the JWT
            JwtClaims claims = new JwtClaims();
            claims.setIssuer("gerenciadorInvestimento");  // who creates the token and signs it
            //claims.setAudience("receiver"); // to whom the token is intended to be sent
            if (tempo_expiracao_minutos != null && tempo_expiracao_minutos > 0) {
                claims.setExpirationTimeMinutesInTheFuture(tempo_expiracao_minutos); // time when the token will expire (10 minutes from now)
            }
            claims.setGeneratedJwtId(); // a unique identifier for the token
            claims.setIssuedAtToNow();  // when the token was issued/created (now)
            //claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
            claims.setSubject("autenticacao"); // the subject/principal is whom the token is about
            claims.setClaim("identificador", autenticacao.getIdentificador());

            // additional claims/attributes about the subject can be added

            //Assinatura
            // A JWT is a JWS and/or a JWE with JSON claims as the payload.
            // In this example it is a JWS nested inside a JWE
            // So we first create a JsonWebSignature object.
            JsonWebSignature jws = new JsonWebSignature();

            // The payload of the JWS is JSON content of the JWT Claims
            jws.setPayload(claims.toJson());
            // The JWT is signed using the sender's private key
            jws.setKey(privateKey);
            // Set the Key ID (kid) header because it's just the polite thing to do.
            // We only have one signing key in this example but a using a Key ID helps
            // facilitate a smooth key rollover process

            // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

            // Sign the JWS and produce the compact serialization, which will be the inner JWT/JWS
            // representation, which is a string consisting of three dot ('.') separated
            // base64url-encoded parts in the form Header.Payload.Signature
            String jwt_assinado = jws.getCompactSerialization();

            //Criptografia
            // The outer JWT is a JWE
            JsonWebEncryption jwe = new JsonWebEncryption();

            // The output of the ECDH-ES key agreement will encrypt a randomly generated content encryption key
            jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A256GCMKW);

            // The content encryption key is used to encrypt the payload
            // with a composite AES-CBC / HMAC SHA2 encryption algorithm
            String encAlg = ContentEncryptionAlgorithmIdentifiers.AES_256_GCM;
            jwe.setEncryptionMethodHeaderParameter(encAlg);

            // We encrypt to the receiver using their public key
            jwe.setKey(publicKey);

            // A nested JWT requires that the cty (Content Type) header be set to "JWT" in the outer JWT
            jwe.setContentTypeHeaderValue("JWT");

            // The inner JWT is the payload of the outer JWT
            jwe.setPayload(jwt_assinado);

            // Produce the JWE compact serialization, which is the complete JWT/JWE representation,
            // which is a string consisting of five dot ('.') separated
            // base64url-encoded parts in the form Header.EncryptedKey.IV.Ciphertext.AuthenticationTag
            String jwt = "Bearer ".concat(jwe.getCompactSerialization());
            return jwt;
        } catch (JoseException e) {
            throw new IllegalStateException(e);
        }
    }

    public Autenticacao validarToken(String token) throws Exception {

        // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
        // be used to validate and process the JWT.
        // The specific validation requirements for a JWT are context dependent, however,
        // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
        // and audience that identifies your system as the intended recipient.
        // It is also typically good to allow only the expected algorithm(s) in the given context
        AlgorithmConstraints jwsAlgConstraints
                = new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                AlgorithmIdentifiers.HMAC_SHA256);
        AlgorithmConstraints jweAlgConstraints
                = new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                KeyManagementAlgorithmIdentifiers.A256GCMKW);
        AlgorithmConstraints jweEncConstraints = new AlgorithmConstraints(
                AlgorithmConstraints.ConstraintType.PERMIT,
                ContentEncryptionAlgorithmIdentifiers.AES_256_GCM);

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                //.setRequireExpirationTime() // the JWT must have an expiration time
                //.setMaxFutureValidityInMinutes(300) // but the  expiration time can't be too crazy
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("gerenciadorInvestimento") // whom the JWT needs to have been issued by
                //.setExpectedAudience("receiver") // to whom the JWT is intended for
                .setVerificationKey(privateKey) // verify the signature with the sender's public key
                .setJwsAlgorithmConstraints(jwsAlgConstraints) // limits the acceptable signature algorithm(s)
                .setJweAlgorithmConstraints(jweAlgConstraints) // limits acceptable encryption key establishment algorithm(s)
                .setJweContentEncryptionAlgorithmConstraints(jweEncConstraints)
                .setDecryptionKey(publicKey)// limits acceptable content encryption algorithm(s)
                .build(); // create the JwtConsumer instance

        try {
            //  Validate the JWT and process it to the Claims
            token = token.substring(token.indexOf(" ") + 1);
            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
            Autenticacao autenticacao = new Autenticacao();
            autenticacao.setIdentificador(jwtClaims.getClaimValue("identificador", String.class));
            autenticacao.setToken(token);
            return autenticacao;
        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            throw new Exception("Token inválido", e);
        }
    }
}
