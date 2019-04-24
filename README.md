## Real time data streaming workshop

### Practical Exercises of Kafka Streams API


#### Exercise 1
    /** *
     * 1. Define properties for application
     * In createTopology method:
     *  a. Create StreamsBuilder instance
     *  b. Create source KStream out of input topic 'ex1-input'
     *  c. Sink kstream to output topic 'ex1-stream-output'
     *  d. Create sourceKTable out of input topic 'ex1-input'
     *  e. Sink ktable to output topic 'ex1-table-output'
     * 2. Create KafkaStreams instance
     * 3. Start kafka streams
     */
#### Exercise 2
    /***
     * Unified orders system produce data to 1 input topic for legacy and new systems
     * Message's key is an indicator of legacy or new system [legacy|new]
     * Messages start with product name (bambi, godzilla, optimus) then is separator':' batch of quantity
     * numbers separated with commas (number can be negative for number of returned pieces) e.q {productName}:1,1,2,-3
     * Filter invalid products as our analysis needs only bambi, godzilla and optimus to be investigated
     * as a result we need create 2 output topics ex2-legacy-out and ex2-new-output each of them
     * should have sum for each of desired products
     *
     * You can assume no malformed records
     *
     */

#### Exercise 3
    /***
     *
     * Create global table of user data and stream of orders then join orders data
     * with inner and left join so that message with remain having key of order
     * and value in format of "Order=orderValue, UserInfo=[userInfoValue]"
     * take into account 'null' values in case of left join
     *
     */
#### Exercise 4
    /***
     * You need to join 2 topics ex4-doctors and ex4-clinics both are json/POJO serialized
     * Serdes and Serializers have already been prepared for you
     * For both doctors and clinics its primary key is used as message key
     * This time key for join is inside of Clinic pojo (dockorId)
     * You will have to figure out how to use Kafka Streams API methods to create one-to-many join
     * Then you will have to flatten that one-to-many structure
     * to get final output topic ex4-output in format:
     * long value key (doctor id)
     * string value message {doctor.id}-{doctor.specialityCode}-{clinic.id}
     *
     */
#### Exercise 5
    /***
     * This time run docker-compose down to shut down previous run
     * and start docker-compose --file docker-compose-ex5.yaml up -d
     *
     * There are 3 topics streamed via kafka connect
     * ex5-doctors, ex5-diseases, ex5-prescriptions
     * Each of them has key in json format {"id": 1} and json value
     * For both ID (ID is name of model for key) and each value format
     * models/serializers/deserializers/serdes have already been prepared for you
     * you will have take a look on data and join/map/aggregate however you want
     * to obtain at the end ex5-output topic where key will be long value diseaseId from Disease pojo
     * and value will be json containing doctorId and prescripted medicineName for that disease
     *
     * Take a note that you will have to introduce new data types, serializers, deserializers and serdes
     * that is easy you can base on implementation done in infra
     *
     * Intentionally there is no test for that exercise
     *
     */

