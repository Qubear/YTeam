// function chengeClass(id){
//     var block=document.getElementById(id);
//     if(document.querySelectorAll('.stocke2').length<6 || block.className=='stocke2'){
//         if(block.className=='stocke'){block.className='stocke2';}
//         else{block.className='stocke';}
//     }
//     else {
//         alert('idi nahoy')
//     }
//     return false;
// }

function selectSit(e) {
    if (!e.target.classList.contains("selected")) {
        e.target.classList.add("selected");
    } else {
        e.target.classList.remove("selected");
    }
}

function getTickets_id() {
    let a=document.querySelectorAll('.selected');
    if (a.length>0){
        let b="";
        a.forEach(function(item, i, a) {
            b+=item.getAttribute('id').toString()+"!"
        });
        console.log(b.toString());
        document.getElementById('-1').value=b.toString();
    }

    return false;
}