import React from 'react'
import ReactDOM from 'react-dom'

import { Button } from '@material-ui/core';
import { ThemeProvider, createMuiTheme} from "@material-ui/core/styles";
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import ChevronRightIcon from '@material-ui/icons/ChevronRight';

import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  },
}));
const theme = createMuiTheme({
  palette: {
    type: "dark",
  },
});



function Square(props) {
  const classes = useStyles();

  return (
    <Grid item xs={4}>
      <Paper className={classes.paper} onClick={props.onClick}>
        {(props.value===null) ? "-" : props.value}
      </Paper>
    </Grid>
  );
}

class Board extends React.Component {
  renderSquare(i) {
    return (
      <Square
        value={this.props.squares[i]}
        onClick={() => this.props.onClick(i)}
      />
    );
  }

  render() {
    return (
      <Paper style={{marginTop: "20px"}}>
        <Grid container spacing={4}>
          {this.renderSquare(0)}
          {this.renderSquare(1)}
          {this.renderSquare(2)}
          {this.renderSquare(3)}
          {this.renderSquare(4)}
          {this.renderSquare(5)}
          {this.renderSquare(6)}
          {this.renderSquare(7)}
          {this.renderSquare(8)}
        </Grid>
      </Paper>
    );
  }
}

class Game extends React.Component {
  
  constructor(props) {
    super(props);
    
    this.state = {
      history: [
        {
          squares: Array(9).fill(null)
        }
      ],
      stepNumber: 0,
      xIsNext: true
    };
  }

  handleClick(i) {
    const history = this.state.history.slice(0, this.state.stepNumber + 1);
    const current = history[history.length - 1];
    const squares = current.squares.slice();
    if (calculateWinner(squares) || squares[i]) {
      return;
    }
    squares[i] = this.state.xIsNext ? "X" : "O";
    this.setState({
      history: history.concat([
        {
          squares: squares
        }
      ]),
      stepNumber: history.length,
      xIsNext: !this.state.xIsNext
    });
  }

  jumpTo(step) {
    this.setState({
      stepNumber: step,
      xIsNext: (step % 2) === 0
    });
  }

  
  render() {
    
    const history = this.state.history;
    const current = history[this.state.stepNumber];
    const winner = calculateWinner(current.squares);
    
    

    const moves = history.map((step, move) => {
      const desc = move ?
        'Go to move #' + move :
        'Go to game start';
      return (
        <ListItem button key={move} onClick={() => this.jumpTo(move)}>
          <ListItemIcon><ChevronRightIcon /></ListItemIcon>
          <ListItemText primary={desc} />
          
        </ListItem>
      );
    });

    let status;
    if (winner) {
      status = "Winner: " + winner;
    } else {
      status = "Next player: " + (this.state.xIsNext ? "X" : "O");
    }
    //<ol>{moves}</ol>
    return (
      <Paper style={{ height: "100vh"}}>
        <Grid container direction="row" justify="space-around" alignItems="flex-start">
          <Grid item xs={1}></Grid>
          <Grid container item xs={8}>
            <Board squares={current.squares} onClick={i => this.handleClick(i)}/>
          </Grid>
          <Grid container item xs alignItems="center">
            <List>
              <ListItem>{status}</ListItem>
              {moves}
            </List>
          </Grid>
        </Grid>
      </Paper>
    );
  }
}

// ========================================

ReactDOM.render(<ThemeProvider theme={theme}><Game /></ThemeProvider>, document.getElementById("root"));

function calculateWinner(squares) {
  const lines = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],
    [2, 4, 6]
  ];
  for (let i = 0; i < lines.length; i++) {
    const [a, b, c] = lines[i];
    if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
      return squares[a];
    }
  }
  return null;
}
