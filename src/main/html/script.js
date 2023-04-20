function loadMural(){
    const queryString = window.location.search;

    const urlParams = new URLSearchParams(queryString);

    const usuario = urlParams.get('usuario')
    document.getElementById("mural-title").innerText += " "+usuario
    applyPosts(usuario)
    }


async function loadMuralSeguindo(){
    const queryString = window.location.search;

    const urlParams = new URLSearchParams(queryString);

    const usuario = urlParams.get('usuario')
    var res = await fetch("/get-seguindo-qnt?nome="+usuario)
    var qntSeguindo = await res.text()
    qntSeguindo = parseInt(qntSeguindo)
    for(var i =0; i<qntSeguindo; i++){
        res = await fetch("/get-seguindo-nome?nome="+usuario+"&nmr="+i)
        var seguindoNome = await res.text()
        applyPosts(seguindoNome)
    }
}

    async function applyPosts(usuario){

        var qntPosts

        var res = await fetch("/get-qnt-posts?nome="+usuario)
        qntPosts = await res.text()
        qntPosts = parseInt(qntPosts)

        for(var i = 0; i<qntPosts; i++){
            const postagemNode = document.createElement("div")
            const nomeNode = document.createElement("h2")
            const textNode = document.createElement("p")

            var res = await fetch("/get-post-nome?nome="+usuario+"&nmr="+i)
            nomeNode.innerText += await res.text()

            var res = await fetch("/get-post-texto?nome="+usuario+"&nmr="+i)
            textNode.innerText = await res.text()
            console.log(textNode.innerText)
            textNode.innerText = unescape(textNode.innerText.replaceAll("+", " "))
            textNode.className+="bg-body-secondary"
            postagemNode.className+="bg-secondary-subtle"
            postagemNode.style.marginBottom = "5vh"
            postagemNode.style.marginLeft = "50%"
            postagemNode.style.translate = "-50% 0"

            postagemNode.appendChild(nomeNode)
            postagemNode.appendChild(textNode)
            document.getElementById("posts").appendChild(postagemNode)


        }

    }
