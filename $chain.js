function $chain(target) {
    let methods = {
        $unchain: () => target
    };
    
    for(let method of Object.getOwnPropertyNames(target.constructor.prototype)) 
       if (typeof target[method] == 'function')
            methods[method] = (...args) => {
                target[method](...args); 
                return methods;
            };

    return methods;
}

// Exemplo:

$chain(new Date())
    .setHours(0)
    .setMinutes(0)
    .setSeconds(0)
    .setMilliseconds(0)
    .$unchain()
    .toLocaleTimeString('pt-BR');

// '00:00:00'
