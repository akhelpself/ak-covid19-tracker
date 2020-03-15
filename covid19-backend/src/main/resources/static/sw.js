'use strict';

const cacheName = 'app-v1'; // change when cached content is updated
const urlsToCache = [
    'manifest.json'
];

self.addEventListener('install', function (ev) {
    ev.waitUntil(caches.open(cacheName).then(function (cache) {
        cache.addAll(urlsToCache.map(function (el) {
            return el;
        }));
    }));
});

self.addEventListener('activate', function (ev) {
    ev.waitUntil(caches.keys().then(function (keyList) {
        keyList.forEach(function (key) {
            if (key !== cacheName) caches.delete(key);
        });
    }));
    return self.clients.claim();
});
self.addEventListener('fetch', function (ev) {

});