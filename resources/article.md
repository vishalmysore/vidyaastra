# Knowledge Graphs, LLMs, and VidyaAstra: A Comprehensive Guide

## Table of Contents
1. [What is a Knowledge Graph?](#what-is-a-knowledge-graph)
2. [Knowledge Graphs and Large Language Models](#knowledge-graphs-and-llms)
3. [Project VidyaAstra](#project-vidyaastra)
4. [VidyaAstra Knowledge Graphs and OWL Ontologies](#vidyaastra-knowledge-graphs-and-owl)
5. [Viewing OWL Ontologies in Protégé](#viewing-owl-in-protege)
6. [Neo4j: Graph Database for Knowledge Representation](#neo4j)

---

## What is a Knowledge Graph?

### Definition
A **Knowledge Graph (KG)** is a structured representation of knowledge in the form of a graph where entities are represented as nodes and relationships between entities are represented as edges. It's a way to organize, store, and reason about information in a machine-readable format.

### Key Components

1. **Nodes (Entities)**: Represent real-world objects, concepts, or things
   - Examples: People, Places, Products, Diseases, Symptoms
   
2. **Edges (Relationships)**: Represent connections or relationships between entities
   - Examples: "Person works_at Company", "Disease causes Symptom", "Product is_part_of Category"

3. **Properties**: Attributes of nodes and edges that provide additional context
   - Examples: A person node might have properties like "age", "name", "email"

### Real-World Examples

- **Google Knowledge Graph**: Powers search result cards with information about entities
- **LinkedIn**: Represents people, companies, jobs, and professional relationships
- **Amazon**: Product relationships, recommendations, and catalog organization
- **Medical Knowledge Graphs**: Disease ontologies, drug interactions, and diagnostic relations

### Benefits of Knowledge Graphs

✓ **Structured Understanding**: Organizes information in a logical, queryable format
✓ **Reasoning Capabilities**: Can derive new knowledge from existing relationships
✓ **Semantic Search**: Find information based on meaning, not just keywords
✓ **Data Integration**: Combines data from multiple sources into a unified structure
✓ **Explainability**: Provides clear paths showing why something is related to something else

---

## Knowledge Graphs and Large Language Models

### The Synergy Between KGs and LLMs

Large Language Models (LLMs) like GPT-4, Claude, and Llama have revolutionized natural language understanding, but they have limitations:

| Aspect | LLMs Alone | With Knowledge Graphs |
|--------|-----------|----------------------|
| **Factual Accuracy** | Can hallucinate/generate false information | Grounds responses in verified facts |
| **Real-time Updates** | Static knowledge from training data | Can incorporate live, current data |
| **Reasoning** | Pattern-based reasoning | Explicit logical reasoning over relationships |
| **Transparency** | Black box decision making | Clear reasoning paths |
| **Domain Specificity** | General knowledge | Deep domain expertise through curated KGs |

### How LLMs Enhance Knowledge Graphs

1. **Information Extraction**: LLMs can automatically extract entities and relationships from unstructured text to populate KGs
2. **Natural Language Queries**: Convert user questions into structured queries that traverse the KG
3. **Knowledge Completion**: Predict missing relationships in the KG based on patterns
4. **Semantic Understanding**: Better understand the meaning and context of relationships

### How Knowledge Graphs Enhance LLMs

1. **Grounding Responses**: LLMs consult the KG to retrieve verified facts before generating responses
2. **Retrieval Augmented Generation (RAG)**: KGs provide context that LLMs use to generate more accurate answers
3. **Fact Verification**: Check LLM outputs against the KG to ensure correctness
4. **Domain-Specific Insights**: Inject specialized domain knowledge into LLM responses

### Practical Applications

- **Healthcare**: LLMs query medical KGs to provide accurate drug interactions and diagnostic information
- **E-commerce**: Knowledge graphs of products help LLMs give personalized recommendations
- **Customer Support**: KGs of FAQs, policies, and solutions help LLMs provide consistent answers
- **Legal/Compliance**: LLMs query regulatory KGs to ensure responses comply with current regulations

---

## Project VidyaAstra

### What is VidyaAstra?

**VidyaAstra** is an innovative open-source project that demonstrates the power of Knowledge Graphs combined with semantic web technologies. It's a comprehensive knowledge management system built with Java and Spring Boot, showcasing how to create, visualize, and query domain-specific knowledge graphs.

### Etymology: What Does "Vidya Astra" Mean?

The name "VidyaAstra" comes from Sanskrit:
- **Vidya** (विद्या) = Knowledge, learning, wisdom
- **Astra** (अस्त्र) = Weapon, tool, instrument

Together, **"Vidya Astra"** translates to **"Weapon of Knowledge"** or **"Knowledge Instrument"** - representing knowledge as a powerful tool for understanding and decision-making.

### Project Overview

VidyaAstra demonstrates:

✓ **Multi-domain Knowledge Graphs**: Multiple datasets across different domains
✓ **OWL Ontologies**: Formal semantic descriptions of domain knowledge
✓ **Interactive Visualization**: Beautiful D3.js visualizations of complex relationships
✓ **Cycle Detection**: Advanced algorithms to detect cycles in knowledge graphs
✓ **RESTful APIs**: Backend services for querying and managing knowledge
✓ **Fraud Detection**: Real-world application using knowledge graph analysis

### Project Structure

```
VidyaAstra/
├── src/main/java/
│   └── vishal/
│       ├── cricket/          # Cricket domain (Teams, Players, Matches)
│       ├── fd/               # Fraud Detection domain
│       ├── hc/               # Healthcare domain (Patients, Medicines, Diagnosis)
│       ├── supermart/        # E-commerce domain (Products, Customers, Departments)
│       └── yoga/             # Yoga domain (Practices, Benefits)
├── resources/
│   ├── healthcare.owl         # OWL ontology for healthcare
│   ├── supermarket.owl        # OWL ontology for supermarket/e-commerce
│   ├── vishal.owl            # OWL ontology for general domain
│   └── nyc_subway.owl        # OWL ontology for NYC Subway system
└── graphs/
    ├── index.html            # Main dashboard
    └── [domain].html         # Interactive visualizations
```

---

## VidyaAstra Knowledge Graphs and OWL Ontologies

### Knowledge Graphs Included in VidyaAstra

VidyaAstra includes **7 interactive knowledge graphs** representing different domains:

#### 1. **Cricket Domain** (`cricket.html`)
- **Entities**: Teams, Players, Matches, Innings, Performances
- **Relationships**: Players play for Teams, Teams play Matches, Players score in Innings
- **Use Case**: Sports analytics, player statistics, team performance analysis
- **OWL File**: Embedded in visualization

#### 2. **Fraud Detection Domain** (`fraud_detection.html`)
- **Entities**: Fraudsters, Transactions, Methods, Prevention Techniques, Detection Methods
- **Relationships**: Fraudster uses Method, Transaction detected by Detection Method
- **Use Case**: Financial fraud detection, risk assessment, compliance monitoring
- **OWL File**: Referenced in fraud detection system
- **Cycle Detection**: Identifies fraud rings and cyclical transaction patterns

#### 3. **Healthcare Domain** (`healthcare.html`)
- **Entities**: Patients, Medicines, Diagnoses, Symptoms, Treatments, Diseases
- **Relationships**: Patient has Symptom, Doctor diagnoses Disease, Patient takes Medicine
- **Use Case**: Medical decision support, drug interaction checking, patient records
- **OWL File**: `resources/healthcare.owl`
- **Features**: Shows disease progression paths, medicine side effects, treatment protocols

#### 4. **Supermarket/E-commerce Domain** (`supermarket.html`)
- **Entities**: Customers, Products, Departments, Brands, Purchases
- **Relationships**: Customer purchases Product, Product in Department, Product from Brand
- **Use Case**: Recommendation systems, inventory management, customer analytics
- **OWL File**: `resources/supermarket.owl`
- **Features**: Cross-sell opportunities, customer segmentation, supply chain

#### 5. **Yoga Domain** (`yoga.html`)
- **Entities**: Yoga Poses, Benefits, Body Parts, Breathing Techniques, Practitioners
- **Relationships**: Pose benefits Body Part, Breathing supports Pose, Practitioner learns Pose
- **Use Case**: Personalized yoga recommendations, injury prevention, wellness programs
- **OWL File**: Embedded in visualization
- **Features**: Benefits tracking, pose difficulty levels, sequencing suggestions

#### 6. **Bird Migration Domain** (`bird_migration.html`)
- **Entities**: Bird Species, Migration Routes, Seasons, Habitats, Stopovers
- **Relationships**: Bird migrates via Route, Route has Stopover, Habitat in Region
- **Use Case**: Conservation, climate impact analysis, ecological studies
- **OWL File**: Can be generated from the graph structure
- **Features**: Seasonal route changes, altitude variations, food source tracking

#### 7. **NYC Subway Passenger Help System** (`index.html`)
- **Entities**: Stations, Services, Accessibility Features, Payment Systems, Emergency Protocols
- **Relationships**: Station provides Service, Service supports Accessibility, Payment accepted at Station
- **Use Case**: Passenger assistance, accessibility information, service navigation
- **OWL File**: `resources/nyc_subway.owl`
- **Features**: Real-time elevator status, fare information, emergency contact info

### Corresponding OWL Ontologies

#### What is OWL?

**OWL (Web Ontology Language)** is a semantic web language used to represent complex knowledge about things, groups, and properties in a formal way that can be processed by computers.

#### OWL Files in VidyaAstra

1. **healthcare.owl**
   - Defines classes: Patient, Medicine, Disease, Symptom, Treatment
   - Properties: hasDiagnosis, prescribedWith, causesSymptom, treatedBy
   - Axioms: Rules about which medicines treat which diseases

2. **supermarket.owl**
   - Defines classes: Product, Customer, Department, Brand, Inventory
   - Properties: purchases, locatedIn, manufactureredBy, hasPrice
   - Axioms: Product pricing rules, inventory constraints

3. **nyc_subway.owl** (Newly Created)
   - Defines classes: SubwayStation, FareService, AccessibilityService, EmergencyService
   - Properties: acceptsFarePayment, supportsAccessibility, providesService
   - Instances: Times Square, Penn Station, Grand Central, etc.
   - Data Properties: isADAAccessible, hasElevators, operatingHours, supportPhoneNumber

#### OWL Features Used

| Feature | Purpose |
|---------|---------|
| **Classes** | Define categories of entities (e.g., Patient, Medicine) |
| **Properties** | Define relationships (Object Properties) and attributes (Data Properties) |
| **Instances** | Specific individuals (e.g., "John" is a Patient) |
| **Axioms** | Rules and constraints (e.g., "All Patients must have at least one Diagnosis") |
| **Restrictions** | Domain and range constraints on properties |
| **Reasoning** | Automatic inference of new facts from rules |

---

## Viewing OWL Ontologies in Protégé

### What is Protégé?

**Protégé** is a free, open-source ontology editor and framework for building intelligent systems. It's the industry standard tool for creating, visualizing, and maintaining OWL ontologies.

### Installation

1. **Download Protégé**
   - Go to: https://protege.stanford.edu/
   - Download the latest version (currently 5.5.0)
   - Available for Windows, Mac, and Linux

2. **System Requirements**
   - Java 8 or later installed
   - Minimum 4GB RAM recommended
   - 500MB free disk space

### How to Open OWL Files in Protégé

#### Method 1: Direct File Opening
1. Launch Protégé
2. Go to **File → Open** (or Ctrl+O)
3. Navigate to your OWL file (e.g., `resources/healthcare.owl`)
4. Click **Open**
5. Protégé will parse and display the ontology

#### Method 2: Recent Files
1. Launch Protégé
2. The recent files appear on the startup screen
3. Click on your OWL file to open

### Key Features in Protégé

#### 1. **Classes Tab**
- View the class hierarchy (taxonomy)
- Add new classes and subclasses
- Define class axioms and restrictions

Example view for healthcare.owl:
```
Patient
  ├── AdultPatient
  ├── PediatricPatient
  └── GeriatricPatient
Medicine
  ├── Prescription
  └── OTC
Disease
  └── ChronicDisease
```

#### 2. **Properties Tab**
- View Object Properties (relationships between entities)
- View Data Properties (attributes with values)
- Define property domain/range restrictions

Example:
```
Object Properties:
  - hasDiagnosis (Domain: Patient, Range: Disease)
  - prescribedWith (Domain: Patient, Range: Medicine)

Data Properties:
  - patientAge (Domain: Patient, Range: int)
  - medicineDosage (Domain: Medicine, Range: string)
```

#### 3. **Individuals Tab**
- View specific instances (individuals)
- Create new individuals
- Assign properties and relationships

Example:
```
Patient: John_Doe
  - age: 45 (years)
  - hasDisease: Diabetes
  - takeMedicine: Metformin

Medicine: Metformin
  - manufacturer: NovoNordisk
  - dosageForm: Tablet
  - treats: Diabetes
```

#### 4. **DL Query Tab**
- Write Description Logic queries to find entities
- Example queries:
  ```
  Patient and hasDiagnosis value Diabetes
  Medicine and treats value Hypertension
  ```

#### 5. **Reasoner Tab**
- Run inference engines (HermiT, Pellet, etc.)
- Check for inconsistencies
- Derive new facts from rules

Steps to use the reasoner:
1. Click **Reasoner** menu
2. Select a reasoner (e.g., "HermiT")
3. Click **Start Reasoner**
4. View inferred classes and properties

### Workflow: Opening NYC Subway OWL

1. Launch Protégé
2. **File → Open** → `C:\work\VidyaAstra\resources\nyc_subway.owl`
3. You'll see:
   - **Classes**: SubwayStation, FareService, AccessibilityService, etc.
   - **Properties**: acceptsFarePayment, supportsAccessibility, etc.
   - **Individuals**: TimesSquare42St, PennStation34StPenn, etc.
4. Click on an individual (e.g., TimesSquare42St) to see:
   - Type: SubwayStation
   - Properties: isADAAccessible (true), hasElevators (true)
   - Relationships: acceptsFarePayment (OMNY Tap), supportsAccessibility (Elevator Status)

### Practical Example: Finding Accessible Stations

**Query in Protégé DL Query:**
```
SubwayStation and isADAAccessible value true
```

**Result:** Returns all stations where the property `isADAAccessible` is true

**Manual verification in individuals:**
- TimesSquare42St → isADAAccessible: true ✓
- GrandCentral42St → isADAAccessible: true ✓
- UnionSq14St → isADAAccessible: false ✗

### Tips for Using Protégé

| Tip | Benefit |
|-----|---------|
| Use **Window → Preferences** to customize the interface | Better workflow |
| Enable **Plugin Manager** for extensions | Add reasoning engines, visualization |
| Use **File → Export** to convert OWL to other formats | RDF/XML, Turtle, etc. |
| Check **OntoGraph** plugin for visual graph representation | Better ontology understanding |
| Use **Explain** button to understand inferred facts | Learn how reasoner works |

---

## Neo4j: Graph Database for Knowledge Representation

### What is Neo4j?

**Neo4j** is the world's leading graph database platform designed specifically for storing, querying, and analyzing connected data. It provides native graph storage and processing capabilities.

### Why Neo4j for Knowledge Graphs?

Knowledge graphs are inherently graph structures, making Neo4j ideal:

| Traditional DB | Neo4j |
|---|---|
| Queries require multiple JOINs | Direct relationship traversal |
| Slow on complex relationships | Fast even with deep relationships |
| Not optimized for graphs | Purpose-built for graphs |
| Example: Finding friend-of-friend takes multiple queries | One relationship traversal |

### Neo4j Core Concepts

#### 1. **Nodes**
Entities in the graph, similar to classes or objects.

```cypher
// Creating a node
(patient:Patient {name: "John", age: 45})

// In the graph: [Patient] → properties: name, age
```

#### 2. **Relationships**
Directed connections between nodes.

```cypher
// Creating a relationship
(patient)-[:HAS_DIAGNOSIS]->(disease:Disease {name: "Diabetes"})

// In the graph: [Patient] -HAS_DIAGNOSIS→ [Disease]
```

#### 3. **Properties**
Key-value pairs attached to nodes and relationships.

```cypher
(patient:Patient {
  name: "John",
  age: 45,
  email: "john@example.com",
  joinDate: "2024-01-15"
})
```

#### 4. **Labels**
Categories that classify nodes.

```
:Patient, :Medicine, :Disease, :Doctor
:Product, :Customer, :Order
:Team, :Player, :Match
```

### Setting Up Neo4j

#### Option 1: Neo4j Desktop (Recommended for Development)

1. **Download**: https://neo4j.com/download/
2. **Install**: Follow installation wizard
3. **Create Database**:
   - Click "New" button
   - Enter database name (e.g., "vidyaastra")
   - Set password
4. **Start Database**: Click play button
5. **Open Neo4j Browser**: Click "Open" → Browser opens at `http://localhost:7687`

#### Option 2: Neo4j Aura (Cloud-Hosted)

1. Go to: https://neo4j.com/cloud/aura/
2. Sign up for free account
3. Create a database instance
4. Get connection credentials
5. Connect from your application

#### Option 3: Docker Container

```bash
docker run -d \
  --name neo4j \
  -p 7474:7474 \
  -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/password123 \
  neo4j:5.1
```

Then access at: `http://localhost:7474/browser`

### Cypher Query Language

**Cypher** is Neo4j's query language, designed to be intuitive and readable.

#### Basic Queries

**1. Create Nodes**
```cypher
CREATE (p:Patient {name: "John Doe", age: 45})
RETURN p
```

**2. Create Relationships**
```cypher
MATCH (p:Patient {name: "John Doe"}), (d:Disease {name: "Diabetes"})
CREATE (p)-[:HAS_DIAGNOSIS]->(d)
RETURN p, d
```

**3. Query Relationships**
```cypher
// Find all patients with diabetes
MATCH (p:Patient)-[:HAS_DIAGNOSIS]->(d:Disease {name: "Diabetes"})
RETURN p.name, d.name
```

**4. Multi-hop Traversals**
```cypher
// Find medicines for patients with diabetes
MATCH (p:Patient)-[:HAS_DIAGNOSIS]->(d:Disease {name: "Diabetes"}),
      (d)<-[:TREATS]-(m:Medicine)
RETURN p.name, m.name, m.dosage
```

**5. Complex Queries**
```cypher
// Find all friends of a person who also like a specific hobby
MATCH (person:Patient {name: "John"})-[:FRIEND_OF]->(friend),
      (friend)-[:LIKES]->(hobby:Hobby {name: "Yoga"})
WHERE friend.age > 30
RETURN friend.name, hobby.name
ORDER BY friend.age DESC
LIMIT 10
```

### Importing VidyaAstra Data into Neo4j

#### Step 1: Connect to Neo4j Browser

Access Neo4j at: `http://localhost:7474/browser`

#### Step 2: Create Healthcare Domain

```cypher
// Create Patients
CREATE (p1:Patient {id: "P001", name: "Alice Johnson", age: 45, status: "Active"})
CREATE (p2:Patient {id: "P002", name: "Bob Smith", age: 60, status: "Active"})
CREATE (p3:Patient {id: "P003", name: "Carol White", age: 35, status: "Inactive"})

// Create Diseases
CREATE (d1:Disease {id: "D001", name: "Diabetes", severity: "High"})
CREATE (d2:Disease {id: "D002", name: "Hypertension", severity: "Medium"})
CREATE (d3:Disease {id: "D003", name: "Arthritis", severity: "Low"})

// Create Medicines
CREATE (m1:Medicine {id: "M001", name: "Metformin", dosage: "500mg"})
CREATE (m2:Medicine {id: "M002", name: "Lisinopril", dosage: "10mg"})
CREATE (m3:Medicine {id: "M003", name: "Ibuprofen", dosage: "200mg"})

// Create Relationships
MATCH (p1:Patient {name: "Alice Johnson"}), (d1:Disease {name: "Diabetes"})
CREATE (p1)-[:HAS_DIAGNOSIS]->(d1)

MATCH (p1:Patient {name: "Alice Johnson"}), (m1:Medicine {name: "Metformin"})
CREATE (p1)-[:TAKES_MEDICINE]->(m1)

MATCH (d1:Disease {name: "Diabetes"}), (m1:Medicine {name: "Metformin"})
CREATE (m1)-[:TREATS]->(d1)
```

#### Step 3: Query the Graph

```cypher
// Find all patients and their diagnoses
MATCH (p:Patient)-[:HAS_DIAGNOSIS]->(d:Disease)
RETURN p.name, d.name, d.severity

// Find medicines for a patient
MATCH (p:Patient {name: "Alice Johnson"})-[:TAKES_MEDICINE]->(m:Medicine)
RETURN m.name, m.dosage

// Find all treatments for diabetes
MATCH (m:Medicine)-[:TREATS]->(d:Disease {name: "Diabetes"})
RETURN m.name, m.dosage
```

### Visualizing Graphs in Neo4j Browser

1. **Open Neo4j Browser**: http://localhost:7474/browser
2. **Run a query**: e.g., `MATCH (n) RETURN n LIMIT 50`
3. **View Results**: Three tabs appear:
   - **Table**: Tabular view of results
   - **Graph**: Visual graph representation
   - **Text**: Raw text output
4. **Interact with Graph**:
   - Drag nodes to reposition
   - Click nodes to expand relationships
   - Click edges to see relationship properties
   - Double-click nodes to focus on them

### Advanced Neo4j Features

#### 1. **Graph Algorithms**
```cypher
// Find shortest path between two nodes
MATCH (start:Patient {name: "Alice"}), (end:Patient {name: "Bob"}),
      path = shortestPath((start)-[*]-(end))
RETURN path, length(path)
```

#### 2. **Cycle Detection**
```cypher
// Find cycles in relationships
MATCH (n)-[r*]->(n)
WHERE length(r) > 1
RETURN n, r
```

#### 3. **Community Detection**
```cypher
// Find groups of related entities
CALL algo.labelPropagation.stream('Patient', 'RELATED_TO')
YIELD nodeId, community
RETURN algo.asNode(nodeId).name, community
```

#### 4. **Recommendations**
```cypher
// Find similar patients based on shared diagnoses
MATCH (p1:Patient)-[:HAS_DIAGNOSIS]->(d:Disease)<-[:HAS_DIAGNOSIS]-(p2:Patient)
WHERE p1 <> p2
WITH p1, p2, count(d) as shared
RETURN p1.name, p2.name, shared
ORDER BY shared DESC
```

### Integration with VidyaAstra

The VidyaAstra project can be enhanced to use Neo4j:

1. **Backend Service** (Java Spring Boot):
```java
@Service
public class KnowledgeGraphService {
    @Autowired
    private Neo4jTemplate neo4jTemplate;
    
    public List<Patient> findPatientsByDisease(String diseaseName) {
        String query = "MATCH (p:Patient)-[:HAS_DIAGNOSIS]->(d:Disease) " +
                      "WHERE d.name = $diseaseName RETURN p";
        return neo4jTemplate.findAll(query, Patient.class);
    }
}
```

2. **Real-time Graph Updates**: Sync OWL ontologies with Neo4j database
3. **Advanced Queries**: Complex pattern matching on healthcare data
4. **Performance**: Fast traversal of multi-hop relationships

### Neo4j vs OWL Ontologies

| Aspect | OWL Ontology | Neo4j Database |
|--------|---|---|
| **Purpose** | Define knowledge structure | Store and query knowledge |
| **Format** | RDF/XML, Turtle | Property Graph |
| **Reasoning** | Automatic inference rules | Query-based computation |
| **Scale** | Small to medium | Large scale |
| **Tools** | Protégé | Neo4j Browser, APIs |
| **Use Case** | Schema definition | Data storage and querying |
| **Best For** | Defining "what is possible" | Storing "what is actual" |

---

## Conclusion

The combination of **Knowledge Graphs**, **OWL Ontologies**, **LLMs**, and **Neo4j** represents a powerful modern approach to knowledge management:

1. **OWL Ontologies** (like in Protégé) define the *structure* and *rules*
2. **Neo4j** stores the *actual data* efficiently
3. **LLMs** provide *natural language interfaces* and *reasoning*
4. **VidyaAstra** demonstrates all these technologies working together

This integrated approach enables:
- ✓ Better understanding of complex domains
- ✓ More accurate AI/ML models through structured knowledge
- ✓ Explainable AI with clear reasoning paths
- ✓ Domain-specific expertise embedded in systems
- ✓ Scalable knowledge management for enterprise applications

**VidyaAstra** shows how these technologies can be combined to create intelligent systems that understand relationships, detect patterns, and provide meaningful insights across multiple domains.

---

## References and Resources

- **Neo4j Official**: https://neo4j.com/
- **Protégé Documentation**: https://protege.stanford.edu/
- **OWL Specification**: https://www.w3.org/OWL/
- **SPARQL Query Language**: https://www.w3.org/TR/sparql11-query/
- **Cypher Query Language**: https://neo4j.com/docs/cypher-manual/
- **VidyaAstra GitHub**: [Your repository link]

---

*Article created on November 9, 2025*
*Part of the VidyaAstra Knowledge Graph Project*

