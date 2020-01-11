const axios = require('axios');
const { Client } = require('pg');
const DOMParser = require('xmldom').DOMParser;
const client = new Client({
    user: 'pg',
    host: '192.168.0.103',
    database: 'cinema',
    password: '12345678',
    port: 64000,
})

const filmIdsKinopoisk = [1112539, 718222, 1255106, 1115685, 835086];
const filmIds = [5, 6, 7, 8, 4];

async function connectToDb() {
    await client.connect();

    for (let i = 0; i < filmIds.length; i++) {
        axios.get(`https://rating.kinopoisk.ru/${filmIdsKinopoisk[i]}.xml`)
            .then((res) => {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(res.data, "text/xml");
                const ratingKinopoisk = xmlDoc.getElementsByTagName("kp_rating")[0].childNodes[0].nodeValue;

                const queryText = `UPDATE film SET ratingfloat = ${ratingKinopoisk} WHERE id = ${filmIds[i]}`;
                client.query(queryText, (err, res) => {
                    if (err) console.log(err);
                    if (res) console.log(`Success update rating for film id: ${filmIds[i]}`);
                });
            })
            .catch((err) => {
                console.log(err);
            });
    }
}

setInterval(connectToDb, 86400000);