function chengeClass(id){
    var block=document.getElementById(id);
    if(document.querySelectorAll('.stocke2').length<6 || block.className=='stocke2'){
        if(block.className=='stocke'){block.className='stocke2';}
        else{block.className='stocke';}
    }
    else {
        alert('idi nahoy')
    }
    return false;
}

