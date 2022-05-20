## business-logic v4

### Kickstoper with Camunda Embedded

Use module bpms to start Kickstoper with Camunda Embedded.

We have created simple starter for Camunda Embedded. It loads the config from file by environment variable
`CAMUNDA_CONFIG` (default value `camunda-config.json`)

run: 
```bash 
java -jar kickstoper-bpms.jar
```

set up config filename: 
```bash 
export CAMUNDA_CONFIG=FILE_NAME
```

Configuration schema:
```json
{
  "datasource": {
    "jdbcUrl": "jdbc:postgresql://localhost:5432/camunda",
    "username": "camunda",
    "password": "camunda",
    "driver": "org.postgresql.Driver",
    "ddl-auto": "validate"
  },
  "processes": [],
  "forms": []
}
```

Where:
1. `datasource` is applied to Camunda ProcessEngine. It is a db where Camunda will store its files
2. `processes` is a list of .bpmn process resources which will be auto deployed on startup
3. `forms` is a list of xml\json forms files which will be auto deployed on startup
