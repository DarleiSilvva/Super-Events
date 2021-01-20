package empresa_x.studio.com.superevents.autenticacao


class Usuario {
    var uuid: String? = null
        private set
    var nomeDoUsuario: String? = null
        private set

    constructor() {}
    constructor(uuid: String?, nomeDoUsuario: String?) {
        this.uuid = uuid
        this.nomeDoUsuario = nomeDoUsuario
    }
}