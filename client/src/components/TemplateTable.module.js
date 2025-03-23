const cards = document.querySelectorAll('.card');

cards.forEach((card) => {
  card.addEventListener('click', () => {
    if (!card.hasAttribute('active')) {
      updateActiveCard(card);
    }
  });
});

function updateActiveCard(activeCard) {
  cards.forEach((card) => {
    if (card === activeCard) {
      card.setAttribute('active', '');
    } else {
      card.removeAttribute('active');
    }
  })
}