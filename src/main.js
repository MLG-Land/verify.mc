// setup dotenv
require('dotenv').config();

// setup a discord bot
const Discord = require('discord.js');
const client = new Discord.Client();
let bot = client;

// import token from env
const token = process.env.TOKEN;

// import config from data/config.jsonc
const config = require('../data/config.jsonc');

// import prefix from config
const prefix = config.prefix;

// get storage type from config
const storageType = config.storage.type;



function onReady() {
    // log the bot's name and tag
    console.log(`Logged in as ${client.user.tag}!`);
}

// call onReady when the bot is ready
client.on('ready', onReady);

client.login(token);
